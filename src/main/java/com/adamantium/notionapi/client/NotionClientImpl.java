package com.adamantium.notionapi.client;

import com.adamantium.notionapi.client.mapper.DatabaseMapper;
import com.adamantium.notionapi.client.mapper.PageMapper;
import com.adamantium.notionapi.client.mapper.PagePropertyMapper;
import com.adamantium.notionapi.client.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.adamantium.notionapi.client.mapper.DatabasePropertyMapper.getDatabasePropertiesFromJson;

public class NotionClientImpl implements NotionClient {

    private final JsonMapper mapper = JsonMapper.builder().build();
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final HttpClient client;

    private List<PropertyMetadata> properties;

    private NotionClientImpl() {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public static NotionClient create() {
        return new NotionClientImpl();
    }

    @Override
    public CompletableFuture<NotionDatabaseProperties> getDatabaseProperties() {
        CompletableFuture<HttpResponse<byte[]>> getDbStage = doGetDatabaseResponse();
        CompletableFuture<HttpResponse<byte[]>> queryDbStage = doQueryDatabaseResponse();
        return getDbStage.thenCombine(queryDbStage, this::buildDbPropertiesResponse);
    }

    @Override
    public CompletableFuture<NotionPage> getPageProperties(String pageId) {
        Map<String,CompletableFuture<HttpResponse<byte[]>>> getPropertyResponses = properties.stream()
                .collect(Collectors.toMap(PropertyMetadata::getId,
                        p -> doGetPagePropertyResponse(pageId, p.getId())));
        CompletableFuture<Void> getPropertiesResponse =
                CompletableFuture.allOf(getPropertyResponses.values().toArray(CompletableFuture[]::new));

        return getPropertiesResponse.thenApply(v -> PageMapper.getPageFromProperties(getPropertyResponses.keySet().stream()
                .map(id -> buildPagePropertyResponse(properties.stream().filter(p -> p.getId().equals(id)).findAny().get(),
                        getPropertyResponses.get(id).join())).toList()));
    }

    @Override
    public CompletableFuture<NotionDatabase> getDatabase(String databaseId) {
        return doGetDatabaseResponse().thenCombine(doQueryDatabaseResponse(), this::buildDbPropertiesResponse)
                .thenCompose(databaseProperties -> {
                    List<CompletableFuture<NotionPage>> getPageResponses = databaseProperties
                            .getPages()
                            .stream()
                            .map(this::getPageProperties)
                            .toList();
                    CompletableFuture<Void> getAllPagesResponse = CompletableFuture.allOf(getPageResponses.toArray(CompletableFuture[]::new));
                    return getAllPagesResponse.thenApply(v -> DatabaseMapper.buildDatabase(databaseProperties,
                            getPageResponses.stream().map(CompletableFuture::join).toList()));
                });
    }

    private NotionPageProperty buildPagePropertyResponse(PropertyMetadata metadata, HttpResponse<byte[]> response) {
        try {
            JsonNode responseJson = mapper.readTree(response.body());
            return PagePropertyMapper.getPropertyFromJson(metadata, responseJson);
        } catch (IOException e) {
            log.throwing(getClass().getName(), "buildPagePropertyResponse", e);
            return null;
        }
    }

    private NotionDatabaseProperties buildDbPropertiesResponse(HttpResponse<byte[]> get, HttpResponse<byte[]> query) {
            try {
                JsonNode getResponseJson = mapper.readTree(get.body());
                JsonNode queryResponseJson = mapper.readTree(query.body());

                NotionDatabaseProperties response = getDatabasePropertiesFromJson(getResponseJson, queryResponseJson);
                properties = response.getProperties();
                return response;
            } catch (IOException e) {
                log.throwing(getClass().getName(), "buildDbPropertiesResponse", e);
                return null;
            }
    }

    private CompletableFuture<HttpResponse<byte[]>> doGetPagePropertyResponse(String pageId, String propertyId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://api.notion.com/v1/pages/%s/properties/%s", pageId, propertyId)))
                    .header("Notion-Version", "2022-06-28")
                    .header("Authorization", "Bearer secret_iwu5RUGMO337kcBTHTZ3NpydaXpfIjh9MeZj8xJ960i")
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (URISyntaxException e) {
            log.throwing(getClass().getName(), "doQueryDatabaseResponse", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private CompletableFuture<HttpResponse<byte[]>> doQueryDatabaseResponse() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.notion.com/v1/databases/639d7b5ab0514b248758a409122dbec6/query"))
                    .header("Notion-Version", "2022-06-28")
                    .header("Authorization", "Bearer secret_iwu5RUGMO337kcBTHTZ3NpydaXpfIjh9MeZj8xJ960i")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (URISyntaxException e) {
            log.throwing(getClass().getName(), "doQueryDatabaseResponse", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    private CompletableFuture<HttpResponse<byte[]>> doGetDatabaseResponse() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.notion.com/v1/databases/639d7b5ab0514b248758a409122dbec6"))
                    .header("Notion-Version", "2022-06-28")
                    .header("Authorization", "Bearer secret_U4XViLNgzGwSIGJwEPMtjqhGzFOidaQnYz5TN8Y9usC")
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (URISyntaxException e) {
            log.throwing(getClass().getName(), "doGetDatabaseResponse", e);
            return CompletableFuture.failedFuture(e);
        }

    }

}

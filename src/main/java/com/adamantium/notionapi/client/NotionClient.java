package com.adamantium.notionapi.client;

import com.adamantium.notionapi.client.model.NotionDatabase;
import com.adamantium.notionapi.client.model.NotionDatabaseProperties;
import com.adamantium.notionapi.client.model.NotionPage;

import java.util.concurrent.CompletableFuture;

public interface NotionClient {

    CompletableFuture<NotionDatabaseProperties> getDatabaseProperties();

    CompletableFuture<NotionPage> getPageProperties(String pageId);

    CompletableFuture<NotionDatabase> getDatabase(String databaseId);

}

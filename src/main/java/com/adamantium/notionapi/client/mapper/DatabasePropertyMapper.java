package com.adamantium.notionapi.client.mapper;

import com.adamantium.notionapi.client.model.NotionDatabaseProperties;
import com.adamantium.notionapi.client.model.PropertyMetadata;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

import static com.adamantium.notionapi.client.model.PropertyType.fromStringValue;

public class DatabasePropertyMapper {

    public static NotionDatabaseProperties getDatabasePropertiesFromJson(JsonNode getDatabaseResponse,
                                                                         JsonNode queryDatabaseResponse) {
        var builder = new NotionDatabaseProperties.Builder();

        String name = getDatabaseResponse.get("title").get(0).get("plain_text").asText();
        builder.setName(name);

        List<PropertyMetadata> pageProperties = new ArrayList<>();
        ObjectNode properties = (ObjectNode) getDatabaseResponse.get("properties");
        properties.forEach(property -> {
            var propertyMetadataBuilder = new PropertyMetadata.Builder()
                    .setName(property.get("name").asText())
                    .setId(property.get("id").asText());
            fromStringValue(property.get("type").asText()).ifPresent(propertyMetadataBuilder::setType);
            pageProperties.add(propertyMetadataBuilder.build());
        });
        builder.addAllProperties(pageProperties);

        List<String> pageIds = new ArrayList<>();
        ArrayNode results = (ArrayNode) queryDatabaseResponse.get("results");
        results.forEach(result -> {
            if (result.get("object").asText().equals("page")) {
                pageIds.add(result.get("id").asText());
            }
        });
        builder.addAllPages(pageIds);

        return builder.build();
    }

}

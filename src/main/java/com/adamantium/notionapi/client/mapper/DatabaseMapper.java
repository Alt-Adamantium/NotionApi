package com.adamantium.notionapi.client.mapper;

import com.adamantium.notionapi.client.model.NotionDatabase;
import com.adamantium.notionapi.client.model.NotionDatabaseProperties;
import com.adamantium.notionapi.client.model.NotionPage;

import java.util.List;

public class DatabaseMapper {

    public static NotionDatabase buildDatabase(NotionDatabaseProperties databaseProperties, List<NotionPage> pages) {
        return new NotionDatabase.Builder()
                .setName(databaseProperties.getName())
                .addAllProperties(databaseProperties.getProperties())
                .addAllPages(pages)
                .build();
    }

}

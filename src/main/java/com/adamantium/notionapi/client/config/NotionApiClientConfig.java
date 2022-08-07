package com.adamantium.notionapi.client.config;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface NotionApiClientConfig {

    String getApiKey();

    List<NotionDatabaseConfig> getDatabaseConfigs();

    class Builder extends NotionApiClientConfig_Builder {}
}


package com.adamantium.notionapi.client.config;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface NotionDatabaseConfig {

    String getId();

    String getName();

    class Builder extends NotionDatabaseConfig_Builder {}
}

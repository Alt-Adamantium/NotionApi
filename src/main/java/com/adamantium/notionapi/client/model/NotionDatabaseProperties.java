package com.adamantium.notionapi.client.model;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface NotionDatabaseProperties {

    String getName();

    List<String> getPages();

    List<PropertyMetadata> getProperties();

    class Builder extends NotionDatabaseProperties_Builder {}
}

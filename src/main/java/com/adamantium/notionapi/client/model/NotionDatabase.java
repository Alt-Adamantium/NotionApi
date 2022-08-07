package com.adamantium.notionapi.client.model;

import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@FreeBuilder
public interface NotionDatabase {

    String getName();

    List<PropertyMetadata> getProperties();

    List<NotionPage> getPages();

    class Builder extends NotionDatabase_Builder {}
}

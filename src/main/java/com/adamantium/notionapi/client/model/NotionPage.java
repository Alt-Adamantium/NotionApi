package com.adamantium.notionapi.client.model;

import org.inferred.freebuilder.FreeBuilder;

import java.util.Map;

@FreeBuilder
public interface NotionPage {

    String getUid();

    String getName();

    Map<String,NotionPageProperty> getProperties();

    class Builder extends NotionPage_Builder {}

}

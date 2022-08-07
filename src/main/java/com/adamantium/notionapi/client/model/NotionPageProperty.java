package com.adamantium.notionapi.client.model;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface NotionPageProperty {

    String getPropertyName();

    String getPropertyValue();

    PropertyType getPropertyType();

    class Builder extends NotionPageProperty_Builder {}
}

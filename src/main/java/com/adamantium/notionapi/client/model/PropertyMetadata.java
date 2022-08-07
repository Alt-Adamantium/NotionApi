package com.adamantium.notionapi.client.model;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface PropertyMetadata {

    String getName();

    String getId();

    PropertyType getType();

    class Builder extends PropertyMetadata_Builder {}
}

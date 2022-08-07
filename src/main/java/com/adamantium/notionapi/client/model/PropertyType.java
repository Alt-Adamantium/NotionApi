package com.adamantium.notionapi.client.model;

import java.util.Arrays;
import java.util.Optional;

public enum PropertyType {

    TITLE("title"),

    FORMULA("formula"),

    MULTI_SELECT("multi_select"),

    NUMBER("number"),

    RELATION("relation"),

    RICH_TEXT("rich_text"),

    SELECT("select");

    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    public static Optional<PropertyType> fromStringValue(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny();
    }

}

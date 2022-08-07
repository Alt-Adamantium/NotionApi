package com.adamantium.notionapi.client.mapper;

import com.adamantium.notionapi.client.model.NotionPageProperty;
import com.adamantium.notionapi.client.model.PropertyMetadata;
import com.adamantium.notionapi.client.model.PropertyType;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.adamantium.notionapi.client.model.PropertyType.*;

public class PagePropertyMapper {

    private static final Map<PropertyType, Function<JsonNode,String>> propertyMap = new HashMap<>() {{
       put(TITLE, PagePropertyMapper::getPropertyValue);
       put(FORMULA, PagePropertyMapper::getFormulaValue);
       put(MULTI_SELECT, PagePropertyMapper::getMultiSelectValue);
       put(NUMBER, PagePropertyMapper::getNumberValue);
       put(RELATION, PagePropertyMapper::getRelationValue);
       put(RICH_TEXT, PagePropertyMapper::getRichTextValue);
       put(SELECT, PagePropertyMapper::getSelectValue);
    }};

    public static NotionPageProperty getPropertyFromJson(PropertyMetadata propertyMetadata,
                                                         JsonNode getPropertyResponse) {
        var builder = new NotionPageProperty.Builder()
                .setPropertyName(propertyMetadata.getName())
                .setPropertyType(propertyMetadata.getType())
                .setPropertyValue(propertyMap.get(propertyMetadata.getType()).apply(getPropertyResponse));
        return builder.build();
    }

    private static String getPropertyValue(JsonNode jsonNode) {
        return jsonNode.get("results").get(0).get("title").get("text").get("content").asText();
    }

    private static String getFormulaValue(JsonNode jsonNode) {
        return jsonNode.get("formula").get("string").asText();
    }

    private static String getMultiSelectValue(JsonNode jsonNode) {
        return jsonNode.get("multi_select").get(0).get("name").asText();
    }

    private static String getNumberValue(JsonNode jsonNode) {
        return jsonNode.get("number").asText();
    }

    private static String getRelationValue(JsonNode jsonNode) {
        List<String> relations = new ArrayList<>();
        jsonNode.get("results").forEach(relation -> {
            relations.add(relation.get("relation").get("id").asText());
        });
        return String.join(" ", relations);
    }

    private static String getRichTextValue(JsonNode jsonNode) {
        return jsonNode.get("results").get(0).get("rich_text").get("text").get("content").asText();
    }

    private static String getSelectValue(JsonNode jsonNode) {
        return jsonNode.get("select").get("name").asText();
    }

}

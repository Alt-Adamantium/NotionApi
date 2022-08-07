package com.adamantium.notionapi.mapper;

import com.adamantium.notionapi.client.mapper.PagePropertyMapper;
import com.adamantium.notionapi.client.model.NotionPageProperty;
import com.adamantium.notionapi.client.model.PropertyMetadata;
import com.adamantium.notionapi.client.model.PropertyType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PagePropertyMapperTest {

    static final String PATH = "src/test/resources/%s";
    private static final JsonMapper mapper = JsonMapper.builder().build();

    @Test
    public void testTitlePropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Title")
                .setId("title")
                .setType(PropertyType.TITLE)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/title_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.TITLE);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());;
    }

    @Test
    public void testFormulaPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Uid")
                .setId("%7Cp%5D%3A")
                .setType(PropertyType.FORMULA)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/formula_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.FORMULA);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    @Test
    public void testMultSelectPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Tags")
                .setId("%3ANu%7D")
                .setType(PropertyType.MULTI_SELECT)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/multiselect_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.MULTI_SELECT);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    @Test
    public void testNumberPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Carbohydrate")
                .setId("%5DH%5Dh")
                .setType(PropertyType.NUMBER)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/number_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.NUMBER);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    @Test
    public void testRelationPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Ingredients")
                .setId("My%5BW")
                .setType(PropertyType.RELATION)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/relation_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.RELATION);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    @Test
    public void testRichTextPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Ratio")
                .setId("R%5BAQ")
                .setType(PropertyType.RICH_TEXT)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/richtext_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.RICH_TEXT);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    @Test
    public void testSelectPropertyConversion() throws IOException {
        PropertyMetadata metadata = new PropertyMetadata.Builder()
                .setName("Type")
                .setId("n%3B%60I")
                .setType(PropertyType.SELECT)
                .build();
        JsonNode getPropertyJson = getJsonFromFile("properties/select_property_response.json");

        NotionPageProperty property = PagePropertyMapper.getPropertyFromJson(metadata, getPropertyJson);

        assertThat(property).isNotNull();
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.SELECT);
        assertThat(property.getPropertyName()).isEqualTo(metadata.getName());
    }

    static JsonNode getJsonFromFile(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(format(PATH, filename)));
        return mapper.readTree(bytes);
    }

}

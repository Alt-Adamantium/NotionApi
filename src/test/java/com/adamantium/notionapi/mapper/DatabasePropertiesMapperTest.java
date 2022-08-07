package com.adamantium.notionapi.mapper;

import com.adamantium.notionapi.client.mapper.DatabasePropertyMapper;
import com.adamantium.notionapi.client.model.NotionDatabaseProperties;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DatabasePropertiesMapperTest {

    @Test
    public void testDatabasePropertiesConversion() throws IOException {
        JsonNode getDatabaseJson = PagePropertyMapperTest.getJsonFromFile("get_database_response.json");
        JsonNode queryDatabaseJson = PagePropertyMapperTest.getJsonFromFile("query_database_response.json");

        NotionDatabaseProperties databaseProperties =
                DatabasePropertyMapper.getDatabasePropertiesFromJson(getDatabaseJson, queryDatabaseJson);

        assertThat(databaseProperties).isNotNull();
        assertThat(databaseProperties.getName()).isEqualTo("Nutrition Database");
        assertThat(databaseProperties.getProperties().isEmpty()).isFalse();
        assertThat(databaseProperties.getPages().isEmpty()).isFalse();
        assertThat(databaseProperties.getPages().size()).isEqualTo(3);
    }

}

package com.adamantium.notionapi.mapper;

import com.adamantium.notionapi.client.mapper.DatabaseMapper;
import com.adamantium.notionapi.client.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DatabaseMapperTest {

    @Test
    public void testDatabaseConversion() {
        NotionDatabaseProperties databaseProperties = new NotionDatabaseProperties.Builder()
                .setName("Database Name")
                .addAllProperties(List.of(new PropertyMetadata.Builder()
                                .setName("Property Name")
                                .setId("Property Id")
                                .setType(PropertyType.RICH_TEXT)
                        .build()))
                .build();

        List<NotionPage> pages = List.of(new NotionPage.Builder()
                        .setName("Page 1")
                        .setUid("Page Id")
                        .putProperties("Property Name", new NotionPageProperty.Builder()
                                .setPropertyName("Property Name")
                                .setPropertyValue("Property Value")
                                .setPropertyType(PropertyType.RICH_TEXT)
                                .build())
                .build());

        NotionDatabase database = DatabaseMapper.buildDatabase(databaseProperties, pages);
        assertThat(database).isNotNull();
        assertThat(database.getName()).isEqualTo(databaseProperties.getName());
        assertThat(database.getPages()).isEqualTo(pages);
        assertThat(database.getProperties()).isEqualTo(databaseProperties.getProperties());

    }

}

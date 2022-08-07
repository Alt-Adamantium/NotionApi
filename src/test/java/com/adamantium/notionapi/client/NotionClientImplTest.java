package com.adamantium.notionapi.client;

import com.adamantium.notionapi.client.model.NotionDatabase;
import com.adamantium.notionapi.client.model.NotionDatabaseProperties;
import com.adamantium.notionapi.client.model.NotionPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NotionClientImplTest {

    private static final String DATABASE_ID = "639d7b5ab0514b248758a409122dbec6";
    private static NotionClient client;

    @BeforeAll
    public static void setUp() {
        client = NotionClientImpl.create();
    }

    @Test
    public void testGetDatabaseProperties() {
        CompletableFuture<NotionDatabaseProperties> propertiesStage = client.getDatabaseProperties();

        NotionDatabaseProperties properties = propertiesStage.join();
        assertThat(properties).isNotNull();

        NotionPage page = client.getPageProperties(properties.getPages().get(0)).join();
        assertThat(page).isNotNull();

    }

    @Test
    public void testGetDatabase() {
        CompletableFuture<NotionDatabase> getDbStage = client.getDatabase(DATABASE_ID);

        NotionDatabase database = getDbStage.join();
        assertThat(database).isNotNull();
    }

}

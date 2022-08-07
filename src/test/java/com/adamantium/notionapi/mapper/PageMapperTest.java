package com.adamantium.notionapi.mapper;

import com.adamantium.notionapi.client.mapper.PageMapper;
import com.adamantium.notionapi.client.model.NotionPage;
import com.adamantium.notionapi.client.model.NotionPageProperty;
import com.adamantium.notionapi.client.model.PropertyType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PageMapperTest {

    @Test
    public void testPageConversionWithMinimumProperties() {
        List<NotionPageProperty> properties = new ArrayList<>();
        NotionPageProperty titleProperty = buildTitleProperty();
        properties.add(titleProperty);
        NotionPageProperty formulaProperty = buildFormulaProperty();
        properties.add(formulaProperty);
        NotionPage page = PageMapper.getPageFromProperties(properties);
        assertThat(page).isNotNull();
        assertThat(page.getName()).isEqualTo(titleProperty.getPropertyValue());
        assertThat(page.getUid()).isEqualTo(formulaProperty.getPropertyValue());
        assertThat(page.getProperties().size()).isEqualTo(0);
    }

    @Test
    public void testPageConversionWithProperties() {
        List<NotionPageProperty> properties = new ArrayList<>();
        NotionPageProperty titleProperty = buildTitleProperty();
        properties.add(titleProperty);
        NotionPageProperty formulaProperty = buildFormulaProperty();
        properties.add(formulaProperty);

        NotionPageProperty numberProperty = new NotionPageProperty.Builder()
                .setPropertyType(PropertyType.NUMBER)
                .setPropertyName("A Number")
                .setPropertyValue("50")
                .build();
        properties.add(numberProperty);
        NotionPage page = PageMapper.getPageFromProperties(properties);
        assertThat(page).isNotNull();
        assertThat(page.getName()).isEqualTo(titleProperty.getPropertyValue());
        assertThat(page.getUid()).isEqualTo(formulaProperty.getPropertyValue());
        assertThat(page.getProperties().size()).isEqualTo(1);
        assertThat(page.getProperties().get(numberProperty.getPropertyName())).isNotNull();
    }

    private NotionPageProperty buildTitleProperty() {
        return new NotionPageProperty.Builder()
                .setPropertyType(PropertyType.TITLE)
                .setPropertyName("Title")
                .setPropertyValue("Name of Record")
                .build();
    }

    private NotionPageProperty buildFormulaProperty() {
        return new NotionPageProperty.Builder()
                .setPropertyType(PropertyType.FORMULA)
                .setPropertyName("Uid")
                .setPropertyValue(UUID.randomUUID().toString())
                .build();
    }

}

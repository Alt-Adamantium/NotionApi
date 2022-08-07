package com.adamantium.notionapi.client.mapper;

import com.adamantium.notionapi.client.model.NotionPage;
import com.adamantium.notionapi.client.model.NotionPageProperty;

import java.util.List;
import java.util.function.Predicate;

import static com.adamantium.notionapi.client.model.PropertyType.FORMULA;
import static com.adamantium.notionapi.client.model.PropertyType.TITLE;

public class PageMapper {

    public static NotionPage getPageFromProperties(List<NotionPageProperty> pageProperties) {
        var builder = new NotionPage.Builder();

        Predicate<NotionPageProperty> isName = property
                -> property.getPropertyType() == TITLE;
        pageProperties.stream().filter(isName)
                .findFirst()
                .ifPresent(property -> builder.setName(property.getPropertyValue()));

        Predicate<NotionPageProperty> isUid = property
                -> property.getPropertyType() == FORMULA
                && property.getPropertyName().equals("Uid");

        pageProperties.stream()
                .filter(isUid)
                .findFirst()
                .ifPresent(property -> builder.setUid(property.getPropertyValue()));

        pageProperties.stream().filter(isName.negate().and(isUid.negate())).forEach(property -> {
            builder.putProperties(property.getPropertyName(), property);
        });

        return builder.build();
    }

}

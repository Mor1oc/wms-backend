package com.warehouse.WMS.component.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ComponentCategoryConverter implements AttributeConverter<ComponentCategory, String> {
    @Override
    public String convertToDatabaseColumn(ComponentCategory category) {
        return category.getTitle();
    }

    @Override
    public ComponentCategory convertToEntityAttribute(String dbData) {
        return ComponentCategory.fromString(dbData);
    }
}

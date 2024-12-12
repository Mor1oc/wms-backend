package com.warehouse.WMS.security.model;

import com.warehouse.WMS.component.model.ComponentCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRoles, String> {
    @Override
    public String convertToDatabaseColumn(UserRoles role) {
        return role.getTittle();
    }

    @Override
    public UserRoles convertToEntityAttribute(String dbData) {
        return UserRoles.fromString(dbData);
    }
}
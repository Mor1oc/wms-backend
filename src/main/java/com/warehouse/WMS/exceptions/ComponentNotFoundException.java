package com.warehouse.WMS.exceptions;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException() {
        super(ErrorMessages.COMPONENT_NOT_FOUND.getMessage());
    }
}

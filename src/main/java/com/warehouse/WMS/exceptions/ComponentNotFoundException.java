package com.warehouse.WMS.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ComponentNotFoundException.class);

    public ComponentNotFoundException() {
        super(ErrorMessages.COMPONENT_NOT_FOUND.getMessage());
        logger.error("Выброшено исключение {}", getClass());
    }
}

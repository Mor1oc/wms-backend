package com.warehouse.WMS.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarehouseNotFoundException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(WarehouseNotFoundException.class);

    public WarehouseNotFoundException() {
        super(ErrorMessages.WAREHOUSE_NOT_FOUND.getMessage());
        logger.error("Выброшено исключение {}", getClass());
    }
}

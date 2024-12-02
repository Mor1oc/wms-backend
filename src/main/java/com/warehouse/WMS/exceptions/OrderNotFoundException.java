package com.warehouse.WMS.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotFoundException.class);

    public OrderNotFoundException() {
        super(ErrorMessages.ORDER_NOT_FOUND.getMessage());
        logger.error("Выброшено исключение {}", getClass());
    }
}

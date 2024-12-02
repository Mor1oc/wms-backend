package com.warehouse.WMS.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    COMPONENT_NOT_FOUND("Комплектующее не найдено"),
    WAREHOUSE_NOT_FOUND("Комплектующее не найдено на складе"),
    ORDER_NOT_FOUND("Заказ не найден");

    private final String message;

    ErrorMessages(String message) {this.message = message;}
}

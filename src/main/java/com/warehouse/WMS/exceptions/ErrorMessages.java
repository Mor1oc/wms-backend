package com.warehouse.WMS.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    COMPONENT_NOT_FOUND("Комплектующее не найдено");

    private final String message;

    ErrorMessages(String message) {this.message = message;}
}

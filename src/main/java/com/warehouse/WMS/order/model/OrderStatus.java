package com.warehouse.WMS.order.model;

import com.warehouse.WMS.component.model.ComponentCategory;

public enum OrderStatus {
    PLANED("Запланирован"),
    IN_PROGRESS("В исполнении"),
    CANCELLED("Отменен"),
    COMPLETED("Завершен"),
    PREPARING("Готовится к отправке");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static OrderStatus fromString(String status) {
        return switch (status) {
            case "Запланирован" -> PLANED;
            case "В исполнении" -> IN_PROGRESS;
            case "Отменен" -> CANCELLED;
            case "Завершен" -> COMPLETED;
            case "Готовится к отправке" -> PREPARING;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return this.title;
    }
}

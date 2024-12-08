package com.warehouse.WMS.order.model;

public enum OrderStatus {
    PLANED("Запланирован"),
    IN_PROGRESS("В исполнении"),
    CANCELLED("Отменен"),
    PREPARING("Готовится к отправке"),
    COMPLETED("Завершен");

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
            case "Готовится к отправке" -> PREPARING;
            case "Завершен" -> COMPLETED;
            default -> null;
        };
    }

    /**
     * "Запланирован" -- 1
     * "В исполнении" -- 2
     * "Отменен" -- 3
     * "Готовится к отправке" -- 4
     * "Завершен" -- 5
     * */
    public static Integer fromStringToId(String status) {
        return switch (status) {
            case "Запланирован" -> 1;
            case "В исполнении" -> 2;
            case "Отменен" -> 3;
            case "Готовится к отправке" -> 4;
            case "Завершен" -> 5;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return this.title;
    }
}

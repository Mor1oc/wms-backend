package com.warehouse.WMS.component.model;

public enum ComponentCategory {
    CPU("Процессор"),
    GPU("Видеокарта"),
    RAM("Оперативная память"),
    MOTHERBOARD("Материнская плата"),
    STORAGE("Накопитель"),
    MOUSE("Компьютерная мышь"),
    MONITOR("Монитор"),
    KEYBOARD("Клавиатура"),
    POWER_SUPPLY_UNIT("Блок питания"),
    COMPUTER_CASE("Корпус"),
    COOLING("Система охлождения");

    private final String title;

    ComponentCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ComponentCategory fromString(String category) {
        return switch (category) {
            case "Процессор" -> CPU;
            case "Видеокарта" -> GPU;
            case "Оперативная память" -> RAM;
            case "Материнская плата" -> MOTHERBOARD;
            case "Накопитель" -> STORAGE;
            case "Компьютерная мышь" -> MOUSE;
            case "Монитор" -> MONITOR;
            case "Клавиатура" -> KEYBOARD;
            case "Блок питания" -> POWER_SUPPLY_UNIT;
            case "Корпус" -> COMPUTER_CASE;
            case "Система охлождения" -> COOLING;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return this.title;
    }
}

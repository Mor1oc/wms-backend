package com.warehouse.WMS.security.model;

import lombok.Getter;

@Getter
public enum UserRoles {
    Manager("Менеджер"),
    StoreKeeper("Кладовщик"),
    Analyst("Аналитик");

    private final String tittle;

    UserRoles(String tittle) {
        this.tittle = tittle;
    }

    /**
     * "manager" -- 1
     * "storeKeeper" -- 2
     * "analyst" -- 3
     */
//     insert into roles (id, roles.role)
//    values (1, "manager"),
//    (2, "storeKeeper"),
//    (3, "analyst");

    public static UserRoles fromString(String role) {
        return switch (role) {
            case "Менеджер" -> Manager;
            case "Кладовщик" -> StoreKeeper;
            case "Аналитик" -> Analyst;
            default -> null;
        };
    }

    public static Integer fromStringToId(String role) {
        return switch (role) {
            case "Менеджер" -> 1;
            case "Кладовщик" -> 2;
            case "Аналитик" -> 3;
            default -> null;
        };
    }
}

package com.warehouse.WMS.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    /**
     * "manager" -- 1
     * "storeKeeper" -- 2
     * "analyst" -- 3
     * */
//     insert into roles (id, roles.role)
//    values (1, "manager"),
//    (2, "storeKeeper"),
//    (3, "analyst");

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role")
    private UserRoles role;

    public Role(Integer id, UserRoles role) {
        this.id = id;
        this.role = role;
    }
}


//CREATE TABLE roles (
//        id INT AUTO_INCREMENT PRIMARY KEY, -- Первичный ключ
//    role VARCHAR(255) NOT NULL         -- Поле для хранения роли
//);
//
//CREATE TABLE user (
//        username VARCHAR(255) PRIMARY KEY, -- Первичный ключ (уникальный идентификатор)
//password VARCHAR(255) NOT NULL,   -- Поле для хранения пароля
//role_id INT,                      -- Внешний ключ для связи с таблицей roles
//FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL
//);
//
//CREATE INDEX idx_role ON roles(role);

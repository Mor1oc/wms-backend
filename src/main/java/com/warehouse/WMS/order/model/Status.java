package com.warehouse.WMS.order.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    /**
     * "Запланирован" -- 1
     * "В исполнении" -- 2
     * "Отменен" -- 3
     * "Завершен" -- 4
     * "Готовится к отправке" -- 5
     * */
//     insert into status (id, status.status)
//    values (1, "Запланирован"),
//    (2, "В исполнении"),
//    (3, "Отменен"),
//    (4, "Готовится к отправке"),
//    (5, "Завершен");

    @Convert(converter = OrderStatusConverter.class)
    @Column(name = "status")
    private OrderStatus status;

    public Status(Integer id) {
        this.id = id;
    }
}

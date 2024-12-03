package com.warehouse.WMS.order.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "order")
    @Column(name = "components_id")
    private List<ComponentQuantity> components;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Order() {

    }

    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.orderDate = orderDTO.getOrderDate();
        this.deliveryDate = orderDTO.getDeliveryDate();
        this.components = new ArrayList<>();
        this.status = new Status(1);
    }
}

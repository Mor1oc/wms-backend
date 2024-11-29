package com.warehouse.WMS.order.model;

import com.warehouse.WMS.component.model.Component;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "component_order")
public class ComponentQuantity {

    @EmbeddedId
    private ComponentQuantityKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("componentId")
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(name = "quantity")
    private int quantity;

    public ComponentQuantity(ComponentQuantityKey id, Order order, Component component, int quantity) {
        this.id = id;
        this.order = order;
        this.component = component;
        this.quantity = quantity;
    }
}

package com.warehouse.WMS.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ComponentQuantityKey implements Serializable {

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "component_id")
    private Integer componentId;

    public ComponentQuantityKey(Integer orderId, Integer componentId) {
        this.orderId = orderId;
        this.componentId = componentId;
    }
}


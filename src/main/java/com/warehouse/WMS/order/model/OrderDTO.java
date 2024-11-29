package com.warehouse.WMS.order.model;

import com.warehouse.WMS.component.model.ComponentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Integer id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private List<ComponentDTO> components;
    private List<Integer> quantities;
    private int totalQuantity;
    private String status;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.deliveryDate = order.getDeliveryDate();
        this.components = order.getComponents()
                .stream()
                .map(ComponentQuantity::getComponent)
                .map(ComponentDTO::new)
                .toList();
        this.quantities = order.getComponents()
                .stream()
                .map(ComponentQuantity::getQuantity)
                .toList();
        this.totalQuantity = order.getComponents()
                .stream()
                .map(ComponentQuantity::getQuantity)
                .reduce(0, Integer::sum);
        this.status = order.getStatus().getStatus().getTitle();
    }
}

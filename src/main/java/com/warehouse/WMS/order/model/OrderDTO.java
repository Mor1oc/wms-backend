package com.warehouse.WMS.order.model;

import com.warehouse.WMS.component.model.ComponentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return totalQuantity == orderDTO.totalQuantity && Objects.equals(id, orderDTO.id) && Objects.equals(orderDate, orderDTO.orderDate) && Objects.equals(deliveryDate, orderDTO.deliveryDate) && Objects.equals(components, orderDTO.components) && Objects.equals(quantities, orderDTO.quantities) && Objects.equals(status, orderDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, deliveryDate, components, quantities, totalQuantity, status);
    }
}

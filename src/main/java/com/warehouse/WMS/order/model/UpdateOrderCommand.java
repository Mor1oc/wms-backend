package com.warehouse.WMS.order.model;

public record UpdateOrderCommand(Integer id, OrderStatus status) {
}

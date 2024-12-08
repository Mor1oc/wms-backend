package com.warehouse.WMS.warehouse.model;

public record UpdateWarehouseQuantityCommand(Integer warehouseId, int quantity) {
}

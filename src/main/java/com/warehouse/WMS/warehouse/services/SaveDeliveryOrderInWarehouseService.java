package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaveDeliveryOrderInWarehouseService implements Executable<Order, Void> {
    private final WarehouseRepository warehouseRepository;

    public SaveDeliveryOrderInWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Void execute(Order order) {
        Set<Integer> componentsIds = order.getComponents()
                .stream()
                .map(componentQuantity -> componentQuantity.getComponent().getId())
                .collect(Collectors.toSet());

        List<Warehouse> warehouses = warehouseRepository.findByComponentIdIn(componentsIds);

        for (Warehouse warehouse : warehouses) {
            var newQuantity = order
                    .getComponents()
                    .stream()
                    .filter(componentQuantity -> componentQuantity.getComponent().getId().equals(warehouse.getComponent().getId()))
                    .findFirst();
            newQuantity.ifPresent(componentQuantity -> warehouse.setQuantity(warehouse.getQuantity() - componentQuantity.getQuantity()));
        }
        List<Warehouse> emptyWarehouses = warehouses.stream()
                .filter(warehouse -> warehouse.getQuantity() == 0)
                .toList();
        warehouseRepository.deleteAll(emptyWarehouses);
        warehouses = warehouses.stream()
                .filter(warehouse -> warehouse.getQuantity() != 0)
                .toList();
        warehouseRepository.saveAll(warehouses);

        return null;
    }
}

package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.order.model.ComponentQuantity;
import com.warehouse.WMS.order.model.ComponentQuantityKey;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaveNewOrderInWarehouseService implements Executable<Order, Void> {
    private final WarehouseRepository warehouseRepository;

    public SaveNewOrderInWarehouseService(WarehouseRepository warehouseRepository) {
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
            newQuantity.ifPresent(componentQuantity -> warehouse.setQuantity(warehouse.getQuantity() + componentQuantity.getQuantity()));
        }
        warehouseRepository.saveAll(warehouses);

        Set<Integer> componentIdsInDb = warehouses.stream()
                .map(warehouse -> warehouse.getComponent().getId())
                .collect(Collectors.toSet());

        var newComponents = order.getComponents() //Получаем комплектующие, которых нет в базе данных
                .stream()
                .filter(component -> !componentIdsInDb.contains(component.getComponent().getId()))
                .toList();

        if (newComponents.isEmpty()) {
            return null;
        }

        var newWarehouses = new ArrayList<Warehouse>();

        for (ComponentQuantity componentQuantity : newComponents) {
            var category = componentQuantity.getComponent().getCategory();
            int rack = switch (category) {
                case CPU, RAM, STORAGE -> 1;
                case MOTHERBOARD, POWER_SUPPLY_UNIT, COOLING -> 3;
                case GPU, COMPUTER_CASE -> 5;
                case MOUSE, MONITOR, KEYBOARD -> 7;
            };
            int cell = switch (category) {
                case CPU, RAM, STORAGE -> 8;
                case MOTHERBOARD, POWER_SUPPLY_UNIT, COOLING -> 4;
                case GPU, COMPUTER_CASE, MOUSE, MONITOR, KEYBOARD -> 2;
            };
            newWarehouses.add(new Warehouse(
                    rack,
                    ThreadLocalRandom.current().nextInt(1, 6),
                    ThreadLocalRandom.current().nextInt(1, 6),
                    cell,
                    componentQuantity.getQuantity(),
                    componentQuantity.getComponent()
            ));
        }

        warehouseRepository.saveAll(newWarehouses);

        return null;
    }
}

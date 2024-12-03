package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.ComponentAndQuantity;
import com.warehouse.WMS.warehouse.model.Warehouse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindShortageOfStockService implements Executable<Void, List<ComponentAndQuantity>> {
    private final WarehouseRepository warehouseRepository;

    public FindShortageOfStockService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<ComponentAndQuantity> execute(Void input) {
        List<ComponentAndQuantity> componentAndQuantityList = new ArrayList<>();
        List<Warehouse> allWarehouses = warehouseRepository.findAll();

        for (Warehouse warehouse : allWarehouses) {
            Component component = warehouse.getComponent();
            int quantity = switch (component.getCategory()) {
                case CPU, MOTHERBOARD, POWER_SUPPLY_UNIT, MOUSE, KEYBOARD -> warehouse.getQuantity() < 10 ? 20 : -1;
                case RAM, STORAGE -> warehouse.getQuantity() < 15 ? 30 : -1;
                case GPU, COOLING, COMPUTER_CASE -> warehouse.getQuantity() < 5 ? 10 : -1;
                case MONITOR -> warehouse.getQuantity() < 3 ? 5 : -1;
            };
            if (quantity > 0)
                componentAndQuantityList.add(new ComponentAndQuantity(component, quantity));
        }

        return componentAndQuantityList;
    }
}

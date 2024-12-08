package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.order.services.GetOrdersByStatusService;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.ComponentAndQuantity;
import com.warehouse.WMS.warehouse.model.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindShortageOfStockService implements Executable<Void, List<ComponentAndQuantity>> {
    private final WarehouseRepository warehouseRepository;

    private static final Logger logger = LoggerFactory.getLogger(FindShortageOfStockService.class);

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
            if (quantity != -1)
                componentAndQuantityList.add(new ComponentAndQuantity(component, quantity));
        }
//        logger.info("componentAndQuantityList: {}", componentAndQuantityList);

        return componentAndQuantityList;
    }
}

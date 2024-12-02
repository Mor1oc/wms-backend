package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.component.services.CreateAllComponentsService;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetWarehousesService implements Query<Void, List<WarehouseDTO>> {
    private final WarehouseRepository warehouseRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetWarehousesService.class);

    public GetWarehousesService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<List<WarehouseDTO>> execute(Void input) {
        logger.info("Получение всех запасов со склада");
        var warehouses = warehouseRepository.findAll();
        var warehouseDTOs = warehouses.stream()
                .map(WarehouseDTO::new)
                .toList();
        return ResponseEntity.ok(warehouseDTOs);
    }
}

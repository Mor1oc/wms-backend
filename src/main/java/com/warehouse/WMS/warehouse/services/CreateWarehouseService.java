package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.services.CreateComponentService;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateWarehouseService implements Command<WarehouseDTO, WarehouseDTO> {
    private final WarehouseRepository warehouseRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateWarehouseService.class);

    public CreateWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<WarehouseDTO> execute(WarehouseDTO warehouseDTO) {
        logger.info("Создание комплектующего на складе {}", warehouseDTO);
        Warehouse warehouse = new Warehouse(warehouseDTO);
        var savedWarehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.ok(new WarehouseDTO(savedWarehouse));
    }
}

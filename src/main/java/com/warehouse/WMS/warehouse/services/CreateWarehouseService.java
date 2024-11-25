package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateWarehouseService implements Command<WarehouseDTO, WarehouseDTO> {
    private final WarehouseRepository warehouseRepository;

    public CreateWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<WarehouseDTO> execute(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse(warehouseDTO);
        var savedWarehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.ok(new WarehouseDTO(savedWarehouse));
    }
}

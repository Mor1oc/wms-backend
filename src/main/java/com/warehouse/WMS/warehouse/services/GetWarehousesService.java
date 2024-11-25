package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetWarehousesService implements Query<Void, List<WarehouseDTO>> {
    private final WarehouseRepository warehouseRepository;

    public GetWarehousesService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<List<WarehouseDTO>> execute(Void input) {
        var warehouses = warehouseRepository.findAll();
        var warehouseDTOs = warehouses.stream()
                .map(WarehouseDTO::new)
                .toList();
        return ResponseEntity.ok(warehouseDTOs);
    }
}

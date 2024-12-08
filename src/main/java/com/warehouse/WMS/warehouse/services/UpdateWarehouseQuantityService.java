package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.exceptions.WarehouseNotFoundException;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.UpdateWarehouseQuantityCommand;
import com.warehouse.WMS.warehouse.model.Warehouse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateWarehouseQuantityService implements Command<UpdateWarehouseQuantityCommand, Integer> {
    private final WarehouseRepository warehouseRepository;

    public UpdateWarehouseQuantityService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<Integer> execute(UpdateWarehouseQuantityCommand input) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(input.warehouseId());
        if (optionalWarehouse.isPresent())
            if (input.quantity() == 0) {
                warehouseRepository.deleteById(input.warehouseId());
                return ResponseEntity.noContent().build();
            } else {
                Warehouse warehouse = optionalWarehouse.get();
                warehouse.setQuantity(input.quantity());
                warehouse = warehouseRepository.save(warehouse);
                return ResponseEntity.ok(warehouse.getQuantity());
            }

        throw new WarehouseNotFoundException();
    }
}

package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.UpdateWarehouseCommand;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateWarehouseService implements Command<UpdateWarehouseCommand, WarehouseDTO> {
    private final WarehouseRepository warehouseRepository;

    public UpdateWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<WarehouseDTO> execute(UpdateWarehouseCommand input) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(input.id());
        if (warehouseOptional.isPresent()) {
            WarehouseDTO warehouseDTO = input.warehouseDTO();
            Component component = new Component(warehouseDTO.getComponent());
            Warehouse warehouse = new Warehouse(warehouseDTO, component);
            warehouse.setId(input.id());
            warehouseRepository.save(warehouse);
            return ResponseEntity.ok(new WarehouseDTO(warehouse));
        }

        return null;
    }
}

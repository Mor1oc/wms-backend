package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.services.CreateAllComponentsService;
import com.warehouse.WMS.exceptions.WarehouseNotFoundException;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.UpdateWarehouseCommand;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateWarehouseService implements Command<UpdateWarehouseCommand, WarehouseDTO> {
    private final WarehouseRepository warehouseRepository;

    private static final Logger logger = LoggerFactory.getLogger(UpdateWarehouseService.class);

    public UpdateWarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<WarehouseDTO> execute(UpdateWarehouseCommand input) {
        logger.info("Попытка найти комплектующее в базе данных с id: {}, и обновить его значение на новые данные: {}", input.id(), input.warehouseDTO());
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(input.id());
        if (warehouseOptional.isPresent()) {
            logger.info("Комплектующее было найдено в базе данных");
            WarehouseDTO warehouseDTO = input.warehouseDTO();
            Component component = new Component(warehouseDTO.getComponent());
            Warehouse warehouse = new Warehouse(warehouseDTO, component);
            warehouse.setId(input.id());
            logger.info("Комплекутющее обновлено");
            warehouseRepository.save(warehouse);
            return ResponseEntity.ok(new WarehouseDTO(warehouse));
        }

        throw new WarehouseNotFoundException();
    }
}

package com.warehouse.WMS.warehouse;

import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import com.warehouse.WMS.warehouse.services.GetWarehousesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {
    private final GetWarehousesService getWarehousesService;

    public WarehouseController(GetWarehousesService getWarehousesService) {
        this.getWarehousesService = getWarehousesService;
    }

    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getComponentsInStock() {
        return getWarehousesService.execute(null);
    }
}

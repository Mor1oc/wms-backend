package com.warehouse.WMS.warehouse;

import com.warehouse.WMS.warehouse.model.UpdateWarehouseQuantityCommand;
import com.warehouse.WMS.warehouse.model.WarehouseAnalysisDTO;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import com.warehouse.WMS.warehouse.services.GetWarehousesService;
import com.warehouse.WMS.warehouse.services.StockAnalysisService;
import com.warehouse.WMS.warehouse.services.UpdateWarehouseQuantityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WarehouseController", description = "CRUD methods with warehouse entities")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {
    private final GetWarehousesService getWarehousesService;
    private final UpdateWarehouseQuantityService updateQuantityWarehouseService;
    private final StockAnalysisService stockAnalysisService;

    public WarehouseController(GetWarehousesService getWarehousesService,
                               UpdateWarehouseQuantityService updateQuantityWarehouseService,
                               StockAnalysisService stockAnalysisService) {
        this.getWarehousesService = getWarehousesService;
        this.updateQuantityWarehouseService = updateQuantityWarehouseService;
        this.stockAnalysisService = stockAnalysisService;
    }

    @Operation(summary = "Получить все запасы со склада")
    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getComponentsInStock() {
        return getWarehousesService.execute(null);
    }

    @Operation(summary = "Изменить количество комплектующего на складе")
    @PutMapping("/warehouse/{id}")
    public ResponseEntity<Integer> updateOrderToComplete(@PathVariable Integer id, @RequestBody Integer quantity) {
        return updateQuantityWarehouseService.execute(new UpdateWarehouseQuantityCommand(id, quantity));
    }

    @Operation(summary = "Получить проанализированные запасы")
    @GetMapping("/warehouse/analysis")
    public ResponseEntity<List<WarehouseAnalysisDTO>> getAnalysisWarehouses() {
        return stockAnalysisService.execute(null);
    }

}

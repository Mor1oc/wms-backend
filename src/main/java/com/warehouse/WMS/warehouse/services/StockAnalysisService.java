package com.warehouse.WMS.warehouse.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseAnalysisDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAnalysisService implements Query<Void, List<WarehouseAnalysisDTO>> {
    private final WarehouseRepository warehouseRepository;

    public StockAnalysisService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public ResponseEntity<List<WarehouseAnalysisDTO>> execute(Void input) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        List<WarehouseAnalysisDTO> warehouseAnalysisDTOs = warehouses.stream()
                .map(WarehouseAnalysisDTO::new)
                .toList();
        for (WarehouseAnalysisDTO warehouse : warehouseAnalysisDTOs) {
            double coverage = calculateCoverage(warehouse);
            warehouse.setCoverage((int) coverage);
        }

        return ResponseEntity.ok(warehouseAnalysisDTOs);
    }

    private static double calculateCoverage(WarehouseAnalysisDTO warehouse) {
        double averageDailyConsumption = switch (warehouse.getComponent().getCategory()) {
            case "Процессор" -> 0.8;
            case "Видеокарта" -> 0.5;
            case "Оперативная память" -> 2.0;
            case "Материнская плата", "Блок питания" -> 0.7;
            case "Накопитель" -> 1.8;
            case "Компьютерная мышь", "Клавиатура" -> 0.9;
            case "Монитор" -> 0.2;
            case "Корпус" -> 0.3;
            case "Система охлаждения" -> 0.4;
            default -> 1;
        };
        return warehouse.getQuantity() / averageDailyConsumption;
    }
}

package com.warehouse.WMS.order;

import com.warehouse.WMS.component.model.ComponentForecastDTO;
import com.warehouse.WMS.order.model.OrderDTO;
import com.warehouse.WMS.order.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "OrderController", description = "CRUD methods with orders")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    private final GetOrdersService getOrdersService;
    private final GetSupplyOrdersService getSupplyOrdersService;
    private final DemandAnalysisOrderService demandAnalysisOrderService;
    private final CreateOrderService createOrderService;
    private final GetOrdersByStatusService getOrdersByStatusService;
    private final UpdateCompleteOrderService updateCompleteOrderService;
    private final UpdateInProgressOrderService updateInProgressOrderService;

    public OrderController(GetOrdersService getOrdersService, GetSupplyOrdersService getSupplyOrdersService, GetShipmentOrdersService getShipmentOrdersService, DemandAnalysisOrderService demandAnalysisOrderService,
                           CreateOrderService createOrderService,
                           GetOrdersByStatusService getOrdersByStatusService,
                           UpdateCompleteOrderService updateOrderInProgressService,
                           UpdateInProgressOrderService updateOrderPreparingService) {
        this.getOrdersService = getOrdersService;
        this.getSupplyOrdersService = getSupplyOrdersService;
        this.demandAnalysisOrderService = demandAnalysisOrderService;
        this.createOrderService = createOrderService;
        this.getOrdersByStatusService = getOrdersByStatusService;
        this.updateCompleteOrderService = updateOrderInProgressService;
        this.updateInProgressOrderService = updateOrderPreparingService;
    }

    @Operation(summary = "Получить все заказы")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return getOrdersService.execute(null);
    }

    @Operation(summary = "Получить все приходы")
    @GetMapping("/orders/supply")
    public ResponseEntity<List<OrderDTO>> getSupplyOrders() {
        return getSupplyOrdersService.execute(null);
    }

    @Operation(summary = "Данные о прогнозах")
    @GetMapping("/orders/forecast")
    public ResponseEntity<Map<String, List<ComponentForecastDTO>>> getForecastOrders() {
        return demandAnalysisOrderService.execute(null);
    }

    @Operation(summary = "Создать заказ")
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return createOrderService.execute(orderDTO);
    }

    @Operation(summary = "Получить все заказы c определенным статусом")
    @GetMapping("/orders/status")
    public ResponseEntity<List<OrderDTO>> getOrderByStatus(@RequestParam String status) {
        return getOrdersByStatusService.execute(status);
    }

    @Operation(summary = "Изменить статус заказа на 'Завершен'")
    @PutMapping("/order/complete/{id}")
    public ResponseEntity<OrderDTO> updateOrderToComplete(@PathVariable Integer id) {
        return updateCompleteOrderService.execute(id);
    }

    @Operation(summary = "Изменить статус заказа на 'В исполении'")
    @PutMapping("/order/in-progress/{id}")
    public ResponseEntity<OrderDTO> updateOrderToInProgress(@PathVariable Integer id) {
        return updateInProgressOrderService.execute(id);
    }
}

package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.OrderDTO;
import com.warehouse.WMS.order.services.GetOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "OrderController", description = "CRUD methods with orders")
@RestController
public class OrderController {
    private final GetOrdersService getOrdersService;

    public OrderController(GetOrdersService getOrdersService) {
        this.getOrdersService = getOrdersService;
    }

    @Operation(summary = "Получить все заказы")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return getOrdersService.execute(null);
    }

}

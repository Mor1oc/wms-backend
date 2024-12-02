package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.OrderDTO;
import com.warehouse.WMS.order.model.OrderStatus;
import com.warehouse.WMS.order.model.UpdateOrderCommand;
import com.warehouse.WMS.order.services.CreateOrderService;
import com.warehouse.WMS.order.services.GetOrdersByStatusService;
import com.warehouse.WMS.order.services.GetOrdersService;
import com.warehouse.WMS.order.services.UpdateOrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OrderController", description = "CRUD methods with orders")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    private final GetOrdersService getOrdersService;
    private final CreateOrderService createOrderService;
    private final GetOrdersByStatusService getOrdersByStatusService;
    private final UpdateOrderStatusService updateOrderStatusService;

    public OrderController(GetOrdersService getOrdersService,
                           CreateOrderService createOrderService,
                           GetOrdersByStatusService getOrdersByStatusService,
                           UpdateOrderStatusService updateOrderStatusService) {
        this.getOrdersService = getOrdersService;
        this.createOrderService = createOrderService;
        this.getOrdersByStatusService = getOrdersByStatusService;
        this.updateOrderStatusService = updateOrderStatusService;
    }

    @Operation(summary = "Получить все заказы")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return getOrdersService.execute(null);
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
        return updateOrderStatusService.execute(new UpdateOrderCommand(id, OrderStatus.COMPLETED));
    }

    @Operation(summary = "Изменить статус заказа на 'В исполении'")
    @PutMapping("/order/in-progress/{id}")
    public ResponseEntity<OrderDTO> updateOrderToInProgress(@PathVariable Integer id) {
        return updateOrderStatusService.execute(new UpdateOrderCommand(id, OrderStatus.IN_PROGRESS));
    }
}

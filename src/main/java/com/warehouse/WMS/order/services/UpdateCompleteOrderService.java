package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.exceptions.OrderNotFoundException;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import com.warehouse.WMS.warehouse.services.SaveNewOrderInWarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCompleteOrderService implements Command<Integer, OrderDTO> {
    private final OrderRepository orderRepository;
    private final SaveNewOrderInWarehouseService saveNewOrderInWarehouseService;

    public UpdateCompleteOrderService(OrderRepository orderRepository,
                                      SaveNewOrderInWarehouseService saveNewOrderInWarehouseService) {
        this.orderRepository = orderRepository;
        this.saveNewOrderInWarehouseService = saveNewOrderInWarehouseService;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            var statusId = OrderStatus.fromStringToId(OrderStatus.COMPLETED.getTitle());

            order.setStatus(new Status(statusId, OrderStatus.COMPLETED));
            order = orderRepository.save(order);

            saveNewOrderInWarehouseService.execute(order);

            return ResponseEntity.ok(new OrderDTO(order));
        }

        throw new OrderNotFoundException();
    }
}

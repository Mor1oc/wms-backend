package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.exceptions.OrderNotFoundException;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateOrderStatusService implements Command<UpdateOrderCommand, OrderDTO> {
    private final OrderRepository orderRepository;

    public UpdateOrderStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(UpdateOrderCommand input) {
        Optional<Order> optionalOrder = orderRepository.findById(input.id());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            var statusId = OrderStatus.fromStringToId(input.status().getTitle());
            order.setStatus(new Status(statusId, input.status()));
            order = orderRepository.save(order);
            return ResponseEntity.ok(new OrderDTO(order));
        }

        throw new OrderNotFoundException();
    }
}

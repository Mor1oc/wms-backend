package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.exceptions.OrderNotFoundException;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UpdateInProgressOrderService implements Command<Integer, OrderDTO> {

    private final OrderRepository orderRepository;

    public UpdateInProgressOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            var statusId = OrderStatus.fromStringToId(OrderStatus.IN_PROGRESS.getTitle());
            order.setStatus(new Status(statusId, OrderStatus.IN_PROGRESS));
            if (order.getOrderDate() == null) {
                order.setOrderDate(LocalDate.now());
                order.setDeliveryDate(LocalDate.now().plusDays(3));
            }
            order = orderRepository.save(order);
            return ResponseEntity.ok(new OrderDTO(order));
        }

        throw new OrderNotFoundException();
    }
}
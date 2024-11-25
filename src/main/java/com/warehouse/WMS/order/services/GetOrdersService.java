package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersService implements Command<Void, List<OrderDTO>> {
    private final OrderRepository orderRepository;

    public GetOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public ResponseEntity<List<OrderDTO>> execute(Void input) {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orders.stream().map(OrderDTO::new).toList();
        return ResponseEntity.ok(orderDTOs);
    }
}

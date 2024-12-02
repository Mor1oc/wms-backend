package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersService implements Command<Void, List<OrderDTO>> {
    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetOrdersService.class);


    public GetOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public ResponseEntity<List<OrderDTO>> execute(Void input) {
        logger.info("Получение всех заказов");
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orders.stream().map(OrderDTO::new).toList();
        return ResponseEntity.ok(orderDTOs);
    }
}

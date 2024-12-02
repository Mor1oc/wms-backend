package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import com.warehouse.WMS.order.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersByStatusService implements Query<String, List<OrderDTO>> {

    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetOrdersByStatusService.class);

    public GetOrdersByStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<List<OrderDTO>> execute(String status) {
        logger.info("Преобразование статуса {} в id", status);
        Integer statusId = OrderStatus.fromStringToId(status);
        logger.info("Получение заказов со статутсом id {}", statusId);
        List<Order> orders = orderRepository.getAllByStatusId(statusId);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::new)
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }
}

package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.exceptions.OrderNotFoundException;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import com.warehouse.WMS.warehouse.services.SaveDeliveryOrderInWarehouseService;
import com.warehouse.WMS.warehouse.services.SaveNewOrderInWarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCompleteOrderService implements Command<Integer, OrderDTO> {
    private final OrderRepository orderRepository;
    private final SaveNewOrderInWarehouseService saveNewOrderInWarehouseService;
    private final SaveDeliveryOrderInWarehouseService saveDeliveryOrderInWarehouseService;

    public UpdateCompleteOrderService(OrderRepository orderRepository,
                                      SaveNewOrderInWarehouseService saveNewOrderInWarehouseService,
                                      SaveDeliveryOrderInWarehouseService saveDeliveryOrderInWarehouseService) {
        this.orderRepository = orderRepository;
        this.saveNewOrderInWarehouseService = saveNewOrderInWarehouseService;
        this.saveDeliveryOrderInWarehouseService = saveDeliveryOrderInWarehouseService;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (OrderStatus.PREPARING.equals(order.getStatus().getStatus())) {
                saveDeliveryOrderInWarehouseService.execute(order);
                order.getComponents().forEach(componentQuantity -> componentQuantity.setQuantity(setNegative(componentQuantity.getQuantity()))); // Чтобы определить, что это была отгрузка
            } else
                saveNewOrderInWarehouseService.execute(order);
            var statusId = OrderStatus.fromStringToId(OrderStatus.COMPLETED.getTitle());

            order.setStatus(new Status(statusId, OrderStatus.COMPLETED));
            order = orderRepository.save(order);

            return ResponseEntity.ok(new OrderDTO(order));
        }

        throw new OrderNotFoundException();
    }

    private Integer setNegative(Integer i) {
        return i > 0 ? -i : i;
    }
}

package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.order.ComponentQuantityRepository;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.ComponentQuantity;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetShipmentOrdersService implements Executable<Void, List<OrderDTO>> {
    private final ComponentQuantityRepository componentQuantityRepository;
    private final OrderRepository orderRepository;

    public GetShipmentOrdersService(ComponentQuantityRepository componentQuantityRepository,
                                    OrderRepository orderRepository) {
        this.componentQuantityRepository = componentQuantityRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> execute(Void input) {
        List<ComponentQuantity> componentQuantities = componentQuantityRepository.findByQuantityLessThan(0);

        List<Order> orders = orderRepository.findOrdersByStatusId(4);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::new)
                .toList();

        List<OrderDTO> allOrderDTOs = new ArrayList<>(componentQuantities.stream()
                .map(componentQuantity -> new OrderDTO(componentQuantity.getOrder()))
                .distinct()
                .toList());

        allOrderDTOs.addAll(orderDTOs);

        return allOrderDTOs;
    }
}

package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.order.ComponentQuantityRepository;
import com.warehouse.WMS.order.model.ComponentQuantity;
import com.warehouse.WMS.order.model.OrderDTO;
import com.warehouse.WMS.order.model.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSupplyOrdersService implements Query<Void, List<OrderDTO>> {
    private final ComponentQuantityRepository componentQuantityRepository;

    public GetSupplyOrdersService(ComponentQuantityRepository componentQuantityRepository) {
        this.componentQuantityRepository = componentQuantityRepository;
    }

    @Override
    public ResponseEntity<List<OrderDTO>> execute(Void input) {
        List<ComponentQuantity> componentQuantities = componentQuantityRepository.findByQuantityGreaterThan(0);

        List<OrderDTO> orderDTOs = componentQuantities.stream()
                .filter(componentQuantity -> !componentQuantity.getOrder().getStatus().getStatus().equals(OrderStatus.PREPARING))
                .map(componentQuantity -> new OrderDTO(componentQuantity.getOrder()))
                .distinct()
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }
}

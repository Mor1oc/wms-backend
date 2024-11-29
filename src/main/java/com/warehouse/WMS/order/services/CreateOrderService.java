package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.order.ComponentQuantityRepository;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.ComponentQuantity;
import com.warehouse.WMS.order.model.ComponentQuantityKey;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CreateOrderService implements Command<OrderDTO, OrderDTO> {
    private final OrderRepository orderRepository;
    private final ComponentRepository componentRepository;
    private final ComponentQuantityRepository componentQuantityRepository;

    public CreateOrderService(OrderRepository orderRepository,
                              ComponentRepository componentRepository,
                              ComponentQuantityRepository componentQuantityRepository) {
        this.orderRepository = orderRepository;
        this.componentRepository = componentRepository;
        this.componentQuantityRepository = componentQuantityRepository;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(OrderDTO orderDTO) {
        var orderComponents = orderDTO.getComponents();
        List<String> componentModels = orderComponents
                .stream()
                .map(ComponentDTO::getModel)
                .toList();
        List<Component> components = componentRepository.findByModelIn(componentModels);
        if (components.isEmpty()) {
            components = orderComponents
                    .stream()
                    .map(Component::new)
                    .toList();
            components = componentRepository.saveAll(components);
        } else if (components.size() < componentModels.size()) {
            var modelsInOrder = new HashSet<>(componentModels);
            var componentsNotInDb = orderComponents
                    .stream()
                    .map(Component::new)
                    .filter((component) -> !(modelsInOrder.contains(component.getModel())))
                    .toList(); // Находим комплектующие, которых нет в базе данных
            components = componentRepository.saveAll(componentsNotInDb);
            components.addAll(componentsNotInDb);
        }
        var order = new Order(orderDTO);
        order = orderRepository.save(order);
        var orderId = order.getId();
        var numberOfComponents = components.size();
        List<ComponentQuantityKey> componentQuantityKeys = new ArrayList<>();
        for (Component component : components) {
            componentQuantityKeys.add(new ComponentQuantityKey(orderId, component.getId()));
        }
        List<ComponentQuantity> componentQuantities = new ArrayList<>();
        for (int i = 0; i < numberOfComponents; i++) {
            componentQuantities.add(new ComponentQuantity(
                    componentQuantityKeys.get(i),
                    order, 
                    components.get(i),
                    orderDTO.getQuantities().get(i)
            ));
        }
        componentQuantityRepository.saveAll(componentQuantities);
        order.setComponents(componentQuantities);
        orderRepository.save(order);
        return ResponseEntity.ok(new OrderDTO(order));
    }
}

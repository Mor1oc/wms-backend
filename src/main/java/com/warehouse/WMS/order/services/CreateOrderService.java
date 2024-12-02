package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.component.services.CreateAllComponentsService;
import com.warehouse.WMS.component.services.GetComponentsByModelService;
import com.warehouse.WMS.exceptions.WarehouseNotFoundException;
import com.warehouse.WMS.order.ComponentQuantityRepository;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.ComponentQuantity;
import com.warehouse.WMS.order.model.ComponentQuantityKey;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.order.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CreateOrderService implements Command<OrderDTO, OrderDTO> {
    private final OrderRepository orderRepository;
    private final GetComponentsByModelService getComponentsByModelService;
    private final CreateAllComponentsService createAllComponentsService;
    private final ComponentQuantityRepository componentQuantityRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderService.class);

    public CreateOrderService(OrderRepository orderRepository,
                              GetComponentsByModelService getComponentsByModelService,
                              CreateAllComponentsService createAllComponentsService,
                              ComponentQuantityRepository componentQuantityRepository) {
        this.orderRepository = orderRepository;
        this.getComponentsByModelService = getComponentsByModelService;
        this.createAllComponentsService = createAllComponentsService;
        this.componentQuantityRepository = componentQuantityRepository;
    }

    @Override
    public ResponseEntity<OrderDTO> execute(OrderDTO orderDTO) {
        var orderComponents = orderDTO.getComponents();
        List<String> componentModels = orderComponents
                .stream()
                .map(ComponentDTO::getModel)
                .toList();
        logger.info("Получение всех комплектующих заказа, которые уже есть в базе данных");
        List<Component> components = getComponentsByModelService.execute(componentModels);
        if (components.isEmpty()) {
            logger.info("Преобразование всех ComponentDTO в Component");
            components = orderComponents
                    .stream()
                    .map(Component::new)
                    .toList();
            components = createAllComponentsService.execute(components);
        } else if (components.size() < componentModels.size()) {
            logger.info("Выявление всех комплетующих, что нет в базе данных");
            var modelsInOrder = new HashSet<>(componentModels);
            var componentsNotInDb = orderComponents
                    .stream()
                    .map(Component::new)
                    .filter((component) -> !(modelsInOrder.contains(component.getModel())))
                    .toList(); // Находим комплектующие, которых нет в базе данных
            components = createAllComponentsService.execute(componentsNotInDb);
            components.addAll(componentsNotInDb);
        }
        logger.info("Устанавливание связей между таблицами заказа и комплектующего");
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
        logger.info("Сохрание заказа в базу данных");
        orderRepository.save(order);
        return ResponseEntity.ok(new OrderDTO(order));
    }
}

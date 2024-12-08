package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.model.ComponentCategory;
import com.warehouse.WMS.order.ComponentQuantityRepository;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import com.warehouse.WMS.warehouse.model.ComponentAndQuantity;
import com.warehouse.WMS.warehouse.services.FindShortageOfStockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AutoCreatingNewOrderService implements Executable<Void, Void> {
    private final OrderRepository orderRepository;
    private final ComponentQuantityRepository componentQuantityRepository;
    private final FindShortageOfStockService findShortageOfStockService;

    public AutoCreatingNewOrderService(OrderRepository orderRepository,
                                       ComponentQuantityRepository componentQuantityRepository,
                                       FindShortageOfStockService findShortageOfStockService) {
        this.orderRepository = orderRepository;
        this.componentQuantityRepository = componentQuantityRepository;
        this.findShortageOfStockService = findShortageOfStockService;
    }

    @Override
    public Void execute(Void input) {
        List<ComponentAndQuantity> tempComponentAndQuantities = findShortageOfStockService.execute(null);

        // Проверка на то существует ли заказ на этот компонент уже или нет

        List<Integer> componentIds = tempComponentAndQuantities.stream()
                .map(component -> component.component().getId())
                .toList();

        List<ComponentQuantity> componentQuantitiesInDB = componentQuantityRepository.findAllByComponentIdIn(componentIds);

        componentQuantitiesInDB = componentQuantitiesInDB.stream()
                .filter(componentQuantity -> componentQuantity.getOrder().getStatus().getStatus().equals(OrderStatus.PLANED)
                )
                .toList();

        Set<Integer> componentsIdsInDb = componentQuantitiesInDB.stream()
                .map(component -> component.getComponent().getId())
                .collect(Collectors.toSet());

        List<ComponentAndQuantity> componentAndQuantities = tempComponentAndQuantities.stream()
                .filter(component -> !componentsIdsInDb.contains(component.component().getId()))
                .toList();

        Set<ComponentCategory> categories = componentAndQuantities.stream()
                .map(component -> component.component().getCategory())
                .collect(Collectors.toSet());

        int one = 0, two = 0, three = 0, four = 0;

        for (ComponentCategory category : categories)
            switch (category) {
                case CPU, MOTHERBOARD, POWER_SUPPLY_UNIT, MOUSE, KEYBOARD -> one++;
                case RAM, STORAGE -> two++;
                case GPU, COOLING, COMPUTER_CASE -> three++;
                case MONITOR -> four++;
            }

        List<Order> orders = new ArrayList<>();

        int numberOfOrders = 0;
        if (one > 0) {
            orders.add(new Order());
            one = numberOfOrders++;
        }
        if (two > 0) {
            orders.add(new Order());
            two = numberOfOrders++;
        }
        if (three > 0) {
            orders.add(new Order());
            three = numberOfOrders++;
        }
        if (four > 0) {
            orders.add(new Order());
            four = numberOfOrders;
        }

        orders = orderRepository.saveAll(orders);

        ArrayList<ComponentQuantity> componentQuantities = new ArrayList<>();

        Order order;
        for (ComponentAndQuantity component : componentAndQuantities) {
            order = switch (component.component().getCategory()) {
                case CPU, MOTHERBOARD, POWER_SUPPLY_UNIT, MOUSE, KEYBOARD -> orders.get(one);
                case RAM, STORAGE -> orders.get(two);
                case GPU, COOLING, COMPUTER_CASE -> orders.get(three);
                case MONITOR -> orders.get(four);
            };
            componentQuantities.add(
                    new ComponentQuantity(
                            new ComponentQuantityKey(order.getId(), component.component().getId()),
                            order,
                            component.component(),
                            component.quantity()
                    ));
            order.getComponents().add(componentQuantities.get(componentQuantities.size() - 1));
            order.setStatus(new Status(1, OrderStatus.PLANED));
        }

        componentQuantityRepository.saveAll(componentQuantities);

        orderRepository.saveAll(orders);

        return null;
    }
}

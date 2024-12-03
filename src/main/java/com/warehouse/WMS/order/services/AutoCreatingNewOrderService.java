package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.Order;
import com.warehouse.WMS.warehouse.model.ComponentAndQuantity;
import com.warehouse.WMS.warehouse.services.FindShortageOfStockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCreatingNewOrderService implements Executable<Void, Void> {
    private final OrderRepository orderRepository;
    private final FindShortageOfStockService findShortageOfStockService;

    public AutoCreatingNewOrderService(OrderRepository orderRepository,
                                       FindShortageOfStockService findShortageOfStockService) {
        this.orderRepository = orderRepository;
        this.findShortageOfStockService = findShortageOfStockService;
    }

    @Override
    public Void execute(Void input) {
        List<ComponentAndQuantity> componentAndQuantities = findShortageOfStockService.execute(null);
        Order order = orderRepository.save(new Order());


        return null;
    }
}

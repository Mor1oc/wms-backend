package com.warehouse.WMS;

import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentCategory;
import com.warehouse.WMS.order.OrderRepository;
import com.warehouse.WMS.order.model.*;
import com.warehouse.WMS.order.services.GetOrdersByStatusService;
import com.warehouse.WMS.warehouse.WarehouseRepository;
import com.warehouse.WMS.warehouse.model.Warehouse;
import com.warehouse.WMS.warehouse.model.WarehouseDTO;
import com.warehouse.WMS.warehouse.services.GetWarehousesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GetOrdersByStatusServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private GetOrdersByStatusService getOrdersByStatusService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_orders_exists_when_get_order_service_return_order_dto() {
        Order order = new Order();
        order.setId(1);
        Status status = new Status();
        status.setId(2);
        status.setStatus(OrderStatus.fromString("В исполнении"));
        order.setStatus(status);
        Component component = new Component();
        component.setId(1);
        component.setName("test");
        component.setCategory(ComponentCategory.CPU);
        component.setModel("test");
        component.setManufacture("test");
        component.setPrice(10);
        order.setComponents(
                List.of(
                        new ComponentQuantity(
                                new ComponentQuantityKey(
                                        order.getId(),
                                        component.getId()
                                ),
                                order,
                                component,
                                10
                        )
                ));


        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.getAllByStatusId(2)).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = getOrdersByStatusService.execute("В исполнении");

        Assertions.assertEquals(ResponseEntity.ok(orders.stream().map(OrderDTO::new).toList()), response);
        // asserts the warehouse repository was called once
        verify(orderRepository, times(1)).getAllByStatusId(2);
    }
}

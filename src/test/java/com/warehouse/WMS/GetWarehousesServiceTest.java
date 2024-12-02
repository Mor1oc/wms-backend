package com.warehouse.WMS;

import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentCategory;
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

public class GetWarehousesServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private GetWarehousesService getWarehousesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_warehouse_exists_when_get_warehouse_service_return_warehouse_dto() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1);
        warehouse1.setRack(1);
        warehouse1.setSection(1);
        warehouse1.setShelf(1);
        warehouse1.setCell(1);
        warehouse1.setQuantity(1);
        Component component1 = new Component();
        component1.setId(1);
        component1.setName("test");
        component1.setCategory(ComponentCategory.CPU);
        component1.setModel("test");
        component1.setManufacture("test");
        component1.setPrice(10);
        warehouse1.setComponent(component1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(2);
        warehouse2.setRack(2);
        warehouse2.setSection(2);
        warehouse2.setShelf(2);
        warehouse2.setCell(2);
        warehouse2.setQuantity(2);
        Component component2 = new Component();
        component2.setId(2);
        component2.setName("test");
        component2.setCategory(ComponentCategory.CPU);
        component2.setModel("test");
        component2.setManufacture("test");
        component2.setPrice(10);
        warehouse2.setComponent(component2);

        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);

        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse1, warehouse2));

        ResponseEntity<List<WarehouseDTO>> response = getWarehousesService.execute(null);

        Assertions.assertEquals(ResponseEntity.ok(warehouses.stream().map(WarehouseDTO::new).toList()), response);
        // asserts the warehouse repository was called once
        verify(warehouseRepository, times(1)).findAll();
    }
}

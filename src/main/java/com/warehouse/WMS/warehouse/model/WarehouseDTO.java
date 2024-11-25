package com.warehouse.WMS.warehouse.model;

import com.warehouse.WMS.component.model.ComponentDTO;
import lombok.Data;

@Data
public class WarehouseDTO {
    private Integer id;
    private int rack;
    private int section;
    private int shelf;
    private int cell;
    private int quantity;
    private ComponentDTO component;

    public WarehouseDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.rack = warehouse.getRack();
        this.section = warehouse.getSection();
        this.shelf = warehouse.getShelf();
        this.cell = warehouse.getCell();
        this.quantity = warehouse.getQuantity();
        this.component = new ComponentDTO(warehouse.getComponent());
    }
}

package com.warehouse.WMS.warehouse.model;

import com.warehouse.WMS.component.model.Component;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rack")
    private int rack;

    @Column(name = "section")
    private int section;

    @Column(name = "shelf")
    private int shelf;

    @Column(name = "cell")
    private int cell;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    public Warehouse(WarehouseDTO warehouseDTO) {
        this.id = warehouseDTO.getId();
        this.rack = warehouseDTO.getId();
        this.section = warehouseDTO.getRack();
        this.shelf = warehouseDTO.getSection();
        this.cell = warehouseDTO.getShelf();
        this.quantity = warehouseDTO.getCell();
        this.component = new Component(warehouseDTO.getComponent());
    }

    public Warehouse(WarehouseDTO warehouseDTO, Component component) {
        this.id = warehouseDTO.getId();
        this.rack = warehouseDTO.getId();
        this.section = warehouseDTO.getRack();
        this.shelf = warehouseDTO.getSection();
        this.cell = warehouseDTO.getShelf();
        this.quantity = warehouseDTO.getCell();
        this.component = component;
    }
}

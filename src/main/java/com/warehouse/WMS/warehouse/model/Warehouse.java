package com.warehouse.WMS.warehouse.model;

import com.warehouse.WMS.component.model.Component;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {

    /**
     * Зона A – Часто используемые мелкие комплектующие
     * Категории:(CPU),(RAM),(STORAGE)
     * Стеллажи: R1–R2
     *
     * Зона B – Среднего размера комплектующие
     * Категории:(MOTHERBOARD),(POWER_SUPPLY_UNIT),(COOLING)
     * Стеллажи: R3–R4
     *
     * Зона C – Габаритные и тяжелые комплектующие
     * Категории:(GPU),(COMPUTER_CASE)
     * Стеллажи: R5–R6
     *
     * Зона D – Периферийные устройства
     * Категории:(MONITOR),(KEYBOARD),(MOUSE)
     * Стеллажи: R7–R8
     *
     * Зона E – Резервная зона
     * Категории:
     * Все категории при переполнении других зон.
     * Стеллажи: R9–R10
     * */

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

    public Warehouse(int rack, int section, int shelf, int cell, int quantity, Component component) {
        this.rack = rack;
        this.section = section;
        this.shelf = shelf;
        this.cell = cell;
        this.quantity = quantity;
        this.component = component;
    }

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

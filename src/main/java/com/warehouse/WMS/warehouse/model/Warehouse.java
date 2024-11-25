package com.warehouse.WMS.warehouse.model;

import com.warehouse.WMS.component.model.Component;
import jakarta.persistence.*;
import lombok.Data;

@Data
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
}

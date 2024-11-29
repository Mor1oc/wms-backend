package com.warehouse.WMS.component.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDTO {
    private Integer id;
    private String name;
    private String category;
    private String manufacture;
    private String model;
    private int price;

    public ComponentDTO(Component component) {
        this.id = component.getId();
        this.name = component.getName();
        this.category = component.getCategory().getTitle();
        this.manufacture = component.getManufacture();
        this.model = component.getModel();
        this.price = component.getPrice();
    }
}

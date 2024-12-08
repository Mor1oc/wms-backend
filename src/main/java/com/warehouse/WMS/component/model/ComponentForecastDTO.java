package com.warehouse.WMS.component.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentForecastDTO {
    private Integer id;
    private String name;
    private String category;
    private String manufacture;
    private String model;
    private int price;
    private int forecast;
    private int trend;

    public ComponentForecastDTO(ComponentDTO component, int forecast) {
        this.id = component.getId();
        this.name = component.getName();
        this.category = component.getCategory();
        this.manufacture = component.getManufacture();
        this.model = component.getModel();
        this.price = component.getPrice();
        this.forecast = forecast;
        this.trend = 0;
    }
}

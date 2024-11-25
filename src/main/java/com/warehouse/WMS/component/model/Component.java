package com.warehouse.WMS.component.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "components")
public class Component {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Имя не должно быть меньше 10 символов")
    @Column(name = "name")
    private String name;

    @JsonDeserialize(using = ComponentCategoryDeserializer.class)
    @Convert(converter = ComponentCategoryConverter.class)
    @NotNull(message = "Категория обязательно для ввода")
    @Column(name = "category")
    private ComponentCategory category;

    @NotNull(message = "Производитель обязательно для ввода")
    @Column(name = "manufacture")
    private String manufacture;

    @NotNull(message = "Модель обязательно для ввода")
    @Column(name = "model")
    private String model;

    @Positive(message = "Цена не может быть отрицательной или равной 0")
    @Column(name = "price")
    private int price;

    public Component(ComponentDTO componentDTO) {
        this.id = componentDTO.getId();
        this.name = componentDTO.getName();
        this.category = ComponentCategory.fromString(componentDTO.getCategory());
        this.manufacture = componentDTO.getManufacture();
        this.model = componentDTO.getModel();
        this.price = componentDTO.getPrice();
    }
}


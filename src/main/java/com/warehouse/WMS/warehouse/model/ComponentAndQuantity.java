package com.warehouse.WMS.warehouse.model;

import com.warehouse.WMS.component.model.Component;

public record ComponentAndQuantity(Component components, int quantity) {
}

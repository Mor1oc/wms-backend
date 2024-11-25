package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.ComponentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentQuantityRepository extends JpaRepository<ComponentQuantity, Integer> {
}

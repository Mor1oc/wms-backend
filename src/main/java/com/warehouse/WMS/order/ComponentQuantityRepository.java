package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.ComponentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentQuantityRepository extends JpaRepository<ComponentQuantity, Integer> {
    List<ComponentQuantity> findAllByComponentIdIn(List<Integer> components);

    List<ComponentQuantity> findByQuantityGreaterThan(int i);

    List<ComponentQuantity> findByQuantityLessThan(int i);
}

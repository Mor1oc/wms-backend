package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.ComponentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentQuantityRepository extends JpaRepository<ComponentQuantity, Integer> {
    List<ComponentQuantity> findAllByComponentIdIn(List<Integer> components);

    List<ComponentQuantity> findByQuantityGreaterThan(int i);

    List<ComponentQuantity> findByQuantityLessThan(int i);
}

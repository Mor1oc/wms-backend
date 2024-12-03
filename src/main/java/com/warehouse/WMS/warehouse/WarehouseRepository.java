package com.warehouse.WMS.warehouse;

import com.warehouse.WMS.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    List<Warehouse> findByComponentIdIn(Set<Integer> Component_Ids);
}

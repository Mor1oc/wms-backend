package com.warehouse.WMS.component;

import com.warehouse.WMS.component.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByModelIn(List<String> componentIds);
}

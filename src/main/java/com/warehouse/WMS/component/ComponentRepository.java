package com.warehouse.WMS.component;

import com.warehouse.WMS.component.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByModelIn(List<String> componentIds);
}

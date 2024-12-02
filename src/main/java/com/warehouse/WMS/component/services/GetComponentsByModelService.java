package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentsByModelService implements Executable<List<String>, List<Component>> {
    private final ComponentRepository componentRepository;

    public GetComponentsByModelService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public List<Component> execute(List<String> models) {
        return componentRepository.findByModelIn(models);
    }
}

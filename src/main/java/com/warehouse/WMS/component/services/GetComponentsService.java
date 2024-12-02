package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentsService implements Executable<Void, List<ComponentDTO>> {
    private final ComponentRepository componentRepository;

    public GetComponentsService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public List<ComponentDTO> execute(Void input) {
        List<Component> components = componentRepository.findAll();
        List<ComponentDTO> componentDTOs = components.stream().map(ComponentDTO::new).toList();

        return componentDTOs;
    }
}

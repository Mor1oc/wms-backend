package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.component.model.UpdateComponentCommand;
import com.warehouse.WMS.exceptions.ComponentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateComponentService implements Executable<UpdateComponentCommand, ComponentDTO> {
    private final ComponentRepository componentRepository;

    public UpdateComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }


    @Override
    public ComponentDTO execute(UpdateComponentCommand input) {
        Optional<Component> componentOptional = componentRepository.findById(input.id());
        if (componentOptional.isPresent()) {
            Component component = input.component();
            component.setId(input.id());
            componentRepository.save(component);
            return new ComponentDTO(component);
        }

        throw new ComponentNotFoundException();
    }
}

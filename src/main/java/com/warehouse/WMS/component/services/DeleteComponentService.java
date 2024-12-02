package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.exceptions.ComponentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteComponentService implements Executable<Integer, Void> {
    private final ComponentRepository componentRepository;

    public DeleteComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public Void execute(Integer id) {
        Optional<Component> componentOptional = componentRepository.findById(id);
        if (componentOptional.isPresent()) {
            componentRepository.deleteById(id);
            return null;
        }

        throw new ComponentNotFoundException();
    }
}

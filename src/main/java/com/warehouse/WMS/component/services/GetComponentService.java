package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.exceptions.ComponentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetComponentService implements Query<Integer, ComponentDTO> {
    private final ComponentRepository componentRepository;

    public GetComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public ResponseEntity<ComponentDTO> execute(Integer id) {
        Optional<Component> componentOptional = componentRepository.findById(id);
        if (componentOptional.isPresent())
            return ResponseEntity.ok(new ComponentDTO(componentOptional.get()));

        throw new ComponentNotFoundException();
    }
}

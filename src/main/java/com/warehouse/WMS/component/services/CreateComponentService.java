package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateComponentService implements Command<Component, ComponentDTO> {
    private final ComponentRepository componentRepository;

    public CreateComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public ResponseEntity<ComponentDTO> execute(Component component) {
        Component savedComponent = componentRepository.save(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ComponentDTO(savedComponent));
    }
}

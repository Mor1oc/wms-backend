package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Command;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.exceptions.ComponentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteComponentService implements Command<Integer, Void> {
    private final ComponentRepository componentRepository;

    public DeleteComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id) {
        Optional<Component> componentOptional = componentRepository.findById(id);
        if (componentOptional.isPresent()) {
            componentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        throw new ComponentNotFoundException();
    }
}

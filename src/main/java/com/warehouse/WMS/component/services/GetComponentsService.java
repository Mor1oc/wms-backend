package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentsService implements Query<Void, List<ComponentDTO>> {
    private final ComponentRepository componentRepository;

    public GetComponentsService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public ResponseEntity<List<ComponentDTO>> execute(Void input) {
        List<Component> components = componentRepository.findAll();
        List<ComponentDTO> componentDTOs = components.stream().map(ComponentDTO::new).toList();

        return ResponseEntity.ok(componentDTOs);
    }
}

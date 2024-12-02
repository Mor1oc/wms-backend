package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateComponentService implements Executable<Component, ComponentDTO> {
    private final ComponentRepository componentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateComponentService.class);

    public CreateComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public ComponentDTO execute(Component component) {
        logger.info("Создание комлектующего {}", component);
        Component savedComponent = componentRepository.save(component);
        return new ComponentDTO(savedComponent);
    }
}

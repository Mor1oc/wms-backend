package com.warehouse.WMS.component.services;

import com.warehouse.WMS.Executable;
import com.warehouse.WMS.component.ComponentRepository;
import com.warehouse.WMS.component.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateAllComponentsService implements Executable<List<Component>, List<Component>> {
    private final ComponentRepository componentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateAllComponentsService.class);

    public CreateAllComponentsService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public List<Component> execute(List<Component> components) {
        logger.info("Сохранение всех комплектующих в базу данных");
        return componentRepository.saveAll(components);
    }
}

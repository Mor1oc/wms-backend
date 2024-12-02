package com.warehouse.WMS.component;

import com.warehouse.WMS.component.model.Component;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.component.model.UpdateComponentCommand;
import com.warehouse.WMS.component.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Tag(name = "ComponentController", description = "CRUD methods with Component")
//@RestController
//public class ComponentController {
//
//    private final GetComponentService getComponentService;
//    private final GetComponentsService getComponentsService;
//    private final CreateComponentService createComponentService;
//    private final UpdateComponentService updateComponentService;
//    private final DeleteComponentService deleteComponentService;
//
//    public ComponentController(GetComponentService getComponentService,
//                               GetComponentsService getComponentsService,
//                               CreateComponentService createComponentService,
//                               UpdateComponentService updateComponentService,
//                               DeleteComponentService deleteComponentService) {
//        this.getComponentService = getComponentService;
//        this.getComponentsService = getComponentsService;
//        this.createComponentService = createComponentService;
//        this.updateComponentService = updateComponentService;
//        this.deleteComponentService = deleteComponentService;
//    }
//
//    @Operation(summary = "Найти комплектующее по id")
//    @GetMapping("/component/{id}")
//    public ResponseEntity<ComponentDTO> getComponentById(@PathVariable Integer id) {
//        return getComponentService.execute(id);
//    }
//
//    @Operation(summary = "Получить все комплектующие")
//    @GetMapping("/components")
//    public ResponseEntity<List<ComponentDTO>> getComponents() {
//        return getComponentsService.execute(null);
//    }
//
//    @Operation(summary = "Создать комплектующее")
//    @PostMapping("/component")
//    public ResponseEntity<ComponentDTO> createComponent(@RequestBody Component component) {
//        return createComponentService.execute(component);
//    }
//
//    @Operation(summary = "Обновить комплектующее")
//    @PutMapping("/component/{id}")
//    public ResponseEntity<ComponentDTO> updateComponent(@PathVariable Integer id, @RequestBody Component component) {
//        return updateComponentService.execute(new UpdateComponentCommand(id, component));
//    }
//
//    @Operation(summary = "Удалить комплектующее")
//    @DeleteMapping("/component/{id}")
//    public ResponseEntity<Void> deleteComponent(@PathVariable Integer id) {
//        return deleteComponentService.execute(id);
//    }
//}

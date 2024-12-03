package com.warehouse.WMS.component;

import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.component.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ComponentController", description = "CRUD methods with Component")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ComponentController {

    private final GetComponentsService getComponentsService;

    public ComponentController(GetComponentsService getComponentsService) {
        this.getComponentsService = getComponentsService;
    }

    @Operation(summary = "Получить все комплектующие")
    @GetMapping("/components")
    public ResponseEntity<List<ComponentDTO>> getComponents() {
        return getComponentsService.execute(null);
    }

}

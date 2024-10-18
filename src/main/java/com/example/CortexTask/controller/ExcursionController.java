package com.example.CortexTask.controller;


import com.example.CortexTask.persistence.dto.ExcursionDTO;
import com.example.CortexTask.service.ExcursionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/excursions")
public class ExcursionController {

    @Autowired
    private ExcursionService excursionService;

    @GetMapping
    public ResponseEntity<List<ExcursionDTO>> getAllExcursions() {
        List<ExcursionDTO> excursions = excursionService.getAllExcursions();
        return ResponseEntity.ok(excursions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExcursionDTO> getExcursionById(@PathVariable Long id) {
        ExcursionDTO excursion = excursionService.getExcursionById(id);
        return ResponseEntity.ok(excursion);
    }

    @PostMapping
    public ResponseEntity<ExcursionDTO> createExcursion(@Valid @RequestBody ExcursionDTO excursionDTO) {
        ExcursionDTO createdExcursion = excursionService.createExcursion(excursionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExcursion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExcursionDTO> updateExcursion(
            @PathVariable Long id,
            @Valid @RequestBody ExcursionDTO excursionDTO
    ) {
        ExcursionDTO updatedExcursion = excursionService.updateExcursion(id, excursionDTO);
        return ResponseEntity.ok(updatedExcursion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExcursion(@PathVariable Long id) {
        excursionService.deleteExcursion(id);
        return ResponseEntity.noContent().build();
    }
}


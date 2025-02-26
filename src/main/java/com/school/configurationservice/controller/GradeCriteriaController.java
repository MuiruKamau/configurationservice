package com.school.configurationservice.controller;

import com.school.configurationservice.dto.GradeCriteriaRequestDTO;
import com.school.configurationservice.dto.GradeCriteriaResponseDTO;
import com.school.configurationservice.service.GradeCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/grade-criteria")
public class GradeCriteriaController {

    private final GradeCriteriaService gradeCriteriaService;

    @Autowired
    public GradeCriteriaController(GradeCriteriaService gradeCriteriaService) {
        this.gradeCriteriaService = gradeCriteriaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')") // Only ADMINISTRATOR can create
    public ResponseEntity<GradeCriteriaResponseDTO> createGradeCriteria(@RequestBody GradeCriteriaRequestDTO gradeCriteriaRequestDTO) {
        GradeCriteriaResponseDTO createdGradeCriteria = gradeCriteriaService.createGradeCriteria(gradeCriteriaRequestDTO);
        return new ResponseEntity<>(createdGradeCriteria, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')") // TEACHER and ADMIN can view
    public ResponseEntity<GradeCriteriaResponseDTO> getGradeCriteriaById(@PathVariable Long id) {
        GradeCriteriaResponseDTO gradeCriteria = gradeCriteriaService.getGradeCriteriaById(id);
        if (gradeCriteria != null) {
            return new ResponseEntity<>(gradeCriteria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')")
    public ResponseEntity<List<GradeCriteriaResponseDTO>> getAllGradeCriteria() {
        List<GradeCriteriaResponseDTO> gradeCriteriaList = gradeCriteriaService.getAllGradeCriteria();
        return new ResponseEntity<>(gradeCriteriaList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')") // Only ADMINISTRATOR can update
    public ResponseEntity<GradeCriteriaResponseDTO> updateGradeCriteria(@PathVariable Long id, @RequestBody GradeCriteriaRequestDTO gradeCriteriaRequestDTO) {
        GradeCriteriaResponseDTO updatedGradeCriteria = gradeCriteriaService.updateGradeCriteria(id, gradeCriteriaRequestDTO);
        if (updatedGradeCriteria != null) {
            return new ResponseEntity<>(updatedGradeCriteria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')") // Only ADMINISTRATOR can delete
    public ResponseEntity<Void> deleteGradeCriteria(@PathVariable Long id) {
        if (gradeCriteriaService.deleteGradeCriteria(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

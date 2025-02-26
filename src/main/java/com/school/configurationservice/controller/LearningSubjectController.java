package com.school.configurationservice.controller;

import com.school.configurationservice.dto.LearningSubjectRequestDTO;
import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.service.LearningSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/learning-subjects")
public class LearningSubjectController {

    private final LearningSubjectService learningSubjectService;

    @Autowired
    public LearningSubjectController(LearningSubjectService learningSubjectService) {
        this.learningSubjectService = learningSubjectService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')") // Only ADMINISTRATOR can create
    public ResponseEntity<LearningSubjectResponseDTO> createLearningSubject(@RequestBody LearningSubjectRequestDTO learningSubjectRequestDTO) {
        LearningSubjectResponseDTO createdLearningSubject = learningSubjectService.createLearningSubject(learningSubjectRequestDTO);
        return new ResponseEntity<>(createdLearningSubject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMINISTRATOR')") // TEACHER and ADMIN can view
    public ResponseEntity<LearningSubjectResponseDTO> getLearningSubjectById(@PathVariable Long id) {
        LearningSubjectResponseDTO learningSubject = learningSubjectService.getLearningSubjectById(id);
        if (learningSubject != null) {
            return new ResponseEntity<>(learningSubject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMINISTRATOR')") // TEACHER and ADMIN can view all
    public ResponseEntity<List<LearningSubjectResponseDTO>> getAllLearningSubjects() {
        List<LearningSubjectResponseDTO> learningSubjects = learningSubjectService.getAllLearningSubjects();
        return new ResponseEntity<>(learningSubjects, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LearningSubjectResponseDTO> updateLearningSubject(@PathVariable Long id, @RequestBody LearningSubjectRequestDTO learningSubjectRequestDTO) {
        LearningSubjectResponseDTO updatedLearningSubject = learningSubjectService.updateLearningSubject(id, learningSubjectRequestDTO);
        if (updatedLearningSubject != null) {
            return new ResponseEntity<>(updatedLearningSubject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteLearningSubject(@PathVariable Long id) {
        if (learningSubjectService.deleteLearningSubject(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

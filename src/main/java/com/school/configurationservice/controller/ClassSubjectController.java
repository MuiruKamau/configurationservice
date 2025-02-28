package com.school.configurationservice.controller;

import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/config/class-subjects")
public class ClassSubjectController {

    private final ClassSubjectService classSubjectService;

    @Autowired
    public ClassSubjectController(ClassSubjectService classSubjectService) {
        this.classSubjectService = classSubjectService;
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> assignSubjectToClass(@RequestParam Long classId, @RequestParam Long subjectId) {
        classSubjectService.assignSubjectToClass(classId, subjectId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')")
    public ResponseEntity<List<LearningSubjectResponseDTO>> getSubjectsByClassId(@PathVariable Long classId) {
        List<LearningSubjectResponseDTO> subjects = classSubjectService.getSubjectsByClassId(classId);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    // Add DELETE endpoints, etc., if needed for unassigning subjects
}


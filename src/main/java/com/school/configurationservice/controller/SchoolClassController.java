package com.school.configurationservice.controller;

import com.school.configurationservice.dto.SchoolClassRequestDTO;
import com.school.configurationservice.dto.SchoolClassResponseDTO;
import com.school.configurationservice.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/school-classes")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @Autowired
    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @PostMapping
    public ResponseEntity<SchoolClassResponseDTO> createSchoolClass(@RequestBody SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClassResponseDTO createdSchoolClass = schoolClassService.createSchoolClass(schoolClassRequestDTO);
        return new ResponseEntity<>(createdSchoolClass, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassResponseDTO> getSchoolClassById(@PathVariable Long id) {
        SchoolClassResponseDTO schoolClass = schoolClassService.getSchoolClassById(id);
        if (schoolClass != null) {
            return new ResponseEntity<>(schoolClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SchoolClassResponseDTO>> getAllSchoolClasses() {
        List<SchoolClassResponseDTO> schoolClasses = schoolClassService.getAllSchoolClasses();
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassResponseDTO> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClassResponseDTO updatedSchoolClass = schoolClassService.updateSchoolClass(id, schoolClassRequestDTO);
        if (updatedSchoolClass != null) {
            return new ResponseEntity<>(updatedSchoolClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable Long id) {
        if (schoolClassService.deleteSchoolClass(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.school.configurationservice.service;

import com.school.configurationservice.dto.SchoolClassRequestDTO;
import com.school.configurationservice.dto.SchoolClassResponseDTO;
import com.school.configurationservice.model.SchoolClass;
import com.school.configurationservice.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClassResponseDTO createSchoolClass(SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClass schoolClass = SchoolClass.builder()
                .name(schoolClassRequestDTO.getName())
                .build();
        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass);
        return new SchoolClassResponseDTO(savedSchoolClass.getId(), savedSchoolClass.getName());
    }

    public SchoolClassResponseDTO getSchoolClassById(Long id) {
        return schoolClassRepository.findById(id)
                .map(schoolClass -> new SchoolClassResponseDTO(schoolClass.getId(), schoolClass.getName()))
                .orElse(null); // Handle not found scenario properly in controller
    }

    public List<SchoolClassResponseDTO> getAllSchoolClasses() {
        return schoolClassRepository.findAll().stream()
                .map(schoolClass -> new SchoolClassResponseDTO(schoolClass.getId(), schoolClass.getName()))
                .collect(Collectors.toList());
    }

    public SchoolClassResponseDTO updateSchoolClass(Long id, SchoolClassRequestDTO schoolClassRequestDTO) {
        return schoolClassRepository.findById(id)
                .map(existingSchoolClass -> {
                    existingSchoolClass.setName(schoolClassRequestDTO.getName());
                    SchoolClass updatedSchoolClass = schoolClassRepository.save(existingSchoolClass);
                    return new SchoolClassResponseDTO(updatedSchoolClass.getId(), updatedSchoolClass.getName());
                })
                .orElse(null); // Handle not found scenario properly in controller
    }

    public boolean deleteSchoolClass(Long id) {
        if (schoolClassRepository.existsById(id)) {
            schoolClassRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

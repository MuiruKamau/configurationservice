package com.school.configurationservice.service;

import com.school.configurationservice.dto.GradeCriteriaRequestDTO;
import com.school.configurationservice.dto.GradeCriteriaResponseDTO;
import com.school.configurationservice.model.GradeCriteria;
import com.school.configurationservice.repository.GradeCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeCriteriaService {

    private final GradeCriteriaRepository gradeCriteriaRepository;

    @Autowired
    public GradeCriteriaService(GradeCriteriaRepository gradeCriteriaRepository) {
        this.gradeCriteriaRepository = gradeCriteriaRepository;
    }

    public GradeCriteriaResponseDTO createGradeCriteria(GradeCriteriaRequestDTO gradeCriteriaRequestDTO) {
        GradeCriteria gradeCriteria = GradeCriteria.builder()
                .minScore(gradeCriteriaRequestDTO.getMinScore())
                .maxScore(gradeCriteriaRequestDTO.getMaxScore())
                .grade(gradeCriteriaRequestDTO.getGrade())
                .points(gradeCriteriaRequestDTO.getPoints())
                .build();
        GradeCriteria savedGradeCriteria = gradeCriteriaRepository.save(gradeCriteria);
        return new GradeCriteriaResponseDTO(savedGradeCriteria.getId(), savedGradeCriteria.getMinScore(), savedGradeCriteria.getMaxScore(), savedGradeCriteria.getGrade(), savedGradeCriteria.getPoints());
    }

    public GradeCriteriaResponseDTO getGradeCriteriaById(Long id) {
        return gradeCriteriaRepository.findById(id)
                .map(gradeCriteria -> new GradeCriteriaResponseDTO(gradeCriteria.getId(), gradeCriteria.getMinScore(), gradeCriteria.getMaxScore(), gradeCriteria.getGrade(), gradeCriteria.getPoints()))
                .orElse(null); // Handle not found scenario properly in controller
    }

    public List<GradeCriteriaResponseDTO> getAllGradeCriteria() {
        return gradeCriteriaRepository.findAll().stream()
                .map(gradeCriteria -> new GradeCriteriaResponseDTO(gradeCriteria.getId(), gradeCriteria.getMinScore(), gradeCriteria.getMaxScore(), gradeCriteria.getGrade(), gradeCriteria.getPoints()))
                .collect(Collectors.toList());
    }

    public GradeCriteriaResponseDTO updateGradeCriteria(Long id, GradeCriteriaRequestDTO gradeCriteriaRequestDTO) {
        return gradeCriteriaRepository.findById(id)
                .map(existingGradeCriteria -> {
                    existingGradeCriteria.setMinScore(gradeCriteriaRequestDTO.getMinScore());
                    existingGradeCriteria.setMaxScore(gradeCriteriaRequestDTO.getMaxScore());
                    existingGradeCriteria.setGrade(gradeCriteriaRequestDTO.getGrade());
                    existingGradeCriteria.setPoints(gradeCriteriaRequestDTO.getPoints());
                    GradeCriteria updatedGradeCriteria = gradeCriteriaRepository.save(existingGradeCriteria);
                    return new GradeCriteriaResponseDTO(updatedGradeCriteria.getId(), updatedGradeCriteria.getMinScore(), updatedGradeCriteria.getMaxScore(), updatedGradeCriteria.getGrade(), updatedGradeCriteria.getPoints());
                })
                .orElse(null); // Handle not found scenario properly in controller
    }

    public boolean deleteGradeCriteria(Long id) {
        if (gradeCriteriaRepository.existsById(id)) {
            gradeCriteriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

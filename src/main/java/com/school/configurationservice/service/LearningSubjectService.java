package com.school.configurationservice.service;

import com.school.configurationservice.dto.LearningSubjectRequestDTO;
import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.model.LearningSubject;
import com.school.configurationservice.repository.LearningSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearningSubjectService {

    private final LearningSubjectRepository learningSubjectRepository;

    @Autowired
    public LearningSubjectService(LearningSubjectRepository learningSubjectRepository) {
        this.learningSubjectRepository = learningSubjectRepository;
    }

    public LearningSubjectResponseDTO createLearningSubject(LearningSubjectRequestDTO learningSubjectRequestDTO) {
        LearningSubject learningSubject = LearningSubject.builder()
                .name(learningSubjectRequestDTO.getName())
                .build();
        LearningSubject savedLearningSubject = learningSubjectRepository.save(learningSubject);
        return new LearningSubjectResponseDTO(savedLearningSubject.getId(), savedLearningSubject.getName());
    }

    public LearningSubjectResponseDTO getLearningSubjectById(Long id) {
        return learningSubjectRepository.findById(id)
                .map(learningSubject -> new LearningSubjectResponseDTO(learningSubject.getId(), learningSubject.getName()))
                .orElse(null);
    }

    public List<LearningSubjectResponseDTO> getAllLearningSubjects() {
        return learningSubjectRepository.findAll().stream()
                .map(learningSubject -> new LearningSubjectResponseDTO(learningSubject.getId(), learningSubject.getName()))
                .collect(Collectors.toList());
    }

    public LearningSubjectResponseDTO updateLearningSubject(Long id, LearningSubjectRequestDTO learningSubjectRequestDTO) {
        return learningSubjectRepository.findById(id)
                .map(existingLearningSubject -> {
                    existingLearningSubject.setName(learningSubjectRequestDTO.getName());
                    LearningSubject updatedLearningSubject = learningSubjectRepository.save(existingLearningSubject);
                    return new LearningSubjectResponseDTO(updatedLearningSubject.getId(), updatedLearningSubject.getName());
                })
                .orElse(null);
    }

    public boolean deleteLearningSubject(Long id) {
        if (learningSubjectRepository.existsById(id)) {
            learningSubjectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public LearningSubjectResponseDTO getLearningSubjectByName(String name) {
        Optional<LearningSubject> learningSubjectOptional = learningSubjectRepository.findByName(name);
        return learningSubjectOptional.map(learningSubject -> new LearningSubjectResponseDTO(learningSubject.getId(), learningSubject.getName()))
                .orElse(null);
    }
}
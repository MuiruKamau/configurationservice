package com.school.configurationservice.service;

import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.model.ClassSubject;
import com.school.configurationservice.model.SchoolClass;
import com.school.configurationservice.model.LearningSubject;
import com.school.configurationservice.repository.ClassSubjectRepository;
import com.school.configurationservice.repository.SchoolClassRepository; //Need SchoolClassRepository
import com.school.configurationservice.repository.LearningSubjectRepository; //Need LearningSubjectRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassSubjectService {

    private final ClassSubjectRepository classSubjectRepository;
    private final SchoolClassRepository schoolClassRepository; // Inject SchoolClassRepository
    private final LearningSubjectRepository learningSubjectRepository; // Inject LearningSubjectRepository

    @Autowired
    public ClassSubjectService(ClassSubjectRepository classSubjectRepository, SchoolClassRepository schoolClassRepository, LearningSubjectRepository learningSubjectRepository) {
        this.classSubjectRepository = classSubjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.learningSubjectRepository = learningSubjectRepository;
    }

    public void assignSubjectToClass(Long classId, Long subjectId) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID: " + classId));
        LearningSubject learningSubject = learningSubjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject ID: " + subjectId));

        ClassSubject classSubject = new ClassSubject(schoolClass, learningSubject);
        classSubjectRepository.save(classSubject);
    }

    public List<LearningSubjectResponseDTO> getSubjectsByClassId(Long classId) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID: " + classId));
        return classSubjectRepository.findBySchoolClass(schoolClass).stream()
                .map(ClassSubject::getLearningSubject)
                .map(subject -> new LearningSubjectResponseDTO(subject.getId(), subject.getName()))
                .collect(Collectors.toList());
    }

    // You can add methods to delete assignments, etc., as needed
}

package com.school.configurationservice.service;

import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.model.StreamSubject;
import com.school.configurationservice.model.Stream;
import com.school.configurationservice.model.LearningSubject;
import com.school.configurationservice.repository.StreamSubjectRepository;
import com.school.configurationservice.repository.StreamRepository; //Need StreamRepository
import com.school.configurationservice.repository.LearningSubjectRepository; //Need LearningSubjectRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StreamSubjectService {

    private final StreamSubjectRepository streamSubjectRepository;
    private final StreamRepository streamRepository; // Inject StreamRepository
    private final LearningSubjectRepository learningSubjectRepository; // Inject LearningSubjectRepository

    @Autowired
    public StreamSubjectService(StreamSubjectRepository streamSubjectRepository, StreamRepository streamRepository, LearningSubjectRepository learningSubjectRepository) {
        this.streamSubjectRepository = streamSubjectRepository;
        this.streamRepository = streamRepository;
        this.learningSubjectRepository = learningSubjectRepository;
    }

    public void assignSubjectToStream(Long streamId, Long subjectId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid stream ID: " + streamId));
        LearningSubject learningSubject = learningSubjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject ID: " + subjectId));

        StreamSubject streamSubject = new StreamSubject(stream, learningSubject);
        streamSubjectRepository.save(streamSubject);
    }

    public List<LearningSubjectResponseDTO> getSubjectsByStreamId(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid stream ID: " + streamId));
        return streamSubjectRepository.findByStream(stream).stream()
                .map(StreamSubject::getLearningSubject)
                .map(subject -> new LearningSubjectResponseDTO(subject.getId(), subject.getName()))
                .collect(Collectors.toList());
    }

    // You can add methods to delete assignments, etc., as needed
}

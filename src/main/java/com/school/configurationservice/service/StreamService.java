package com.school.configurationservice.service;

import com.school.configurationservice.dto.StreamRequestDTO;
import com.school.configurationservice.dto.StreamResponseDTO;
import com.school.configurationservice.model.SchoolClass;
import com.school.configurationservice.model.Stream;
import com.school.configurationservice.repository.SchoolClassRepository;
import com.school.configurationservice.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StreamService {

    private final StreamRepository streamRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Autowired
    public StreamService(StreamRepository streamRepository, SchoolClassRepository schoolClassRepository) {
        this.streamRepository = streamRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public StreamResponseDTO createStream(StreamRequestDTO streamRequestDTO) {
        SchoolClass schoolClass = schoolClassRepository.findById(streamRequestDTO.getSchoolClassId())
                .orElseThrow(() -> new IllegalArgumentException("SchoolClass not found with id: " + streamRequestDTO.getSchoolClassId())); // Or handle more gracefully

        Stream stream = Stream.builder()
                .name(streamRequestDTO.getName())
                .schoolClass(schoolClass)
                .build();
        Stream savedStream = streamRepository.save(stream);
        return new StreamResponseDTO(savedStream.getId(), savedStream.getName(), savedStream.getSchoolClass().getId());
    }

    public StreamResponseDTO getStreamById(Long id) {
        return streamRepository.findById(id)
                .map(stream -> new StreamResponseDTO(stream.getId(), stream.getName(), stream.getSchoolClass().getId()))
                .orElse(null); // Handle not found scenario properly in controller
    }

    public List<StreamResponseDTO> getAllStreams() {
        return streamRepository.findAll().stream()
                .map(stream -> new StreamResponseDTO(stream.getId(), stream.getName(), stream.getSchoolClass().getId()))
                .collect(Collectors.toList());
    }

    public StreamResponseDTO updateStream(Long id, StreamRequestDTO streamRequestDTO) {
        return streamRepository.findById(id)
                .map(existingStream -> {
                    SchoolClass schoolClass = schoolClassRepository.findById(streamRequestDTO.getSchoolClassId())
                            .orElseThrow(() -> new IllegalArgumentException("SchoolClass not found with id: " + streamRequestDTO.getSchoolClassId()));
                    existingStream.setName(streamRequestDTO.getName());
                    existingStream.setSchoolClass(schoolClass);
                    Stream updatedStream = streamRepository.save(existingStream);
                    return new StreamResponseDTO(updatedStream.getId(), updatedStream.getName(), updatedStream.getSchoolClass().getId());
                })
                .orElse(null); // Handle not found scenario properly in controller
    }

    public boolean deleteStream(Long id) {
        if (streamRepository.existsById(id)) {
            streamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

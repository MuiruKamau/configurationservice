package com.school.configurationservice.controller;

import com.school.configurationservice.dto.LearningSubjectResponseDTO;
import com.school.configurationservice.service.StreamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/config/stream-subjects")
public class StreamSubjectController {

    private final StreamSubjectService streamSubjectService;

    @Autowired
    public StreamSubjectController(StreamSubjectService streamSubjectService) {
        this.streamSubjectService = streamSubjectService;
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> assignSubjectToStream(@RequestParam Long streamId, @RequestParam Long subjectId) {
        streamSubjectService.assignSubjectToStream(streamId, subjectId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stream/{streamId}")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')")
    public ResponseEntity<List<LearningSubjectResponseDTO>> getSubjectsByStreamId(@PathVariable Long streamId) {
        List<LearningSubjectResponseDTO> subjects = streamSubjectService.getSubjectsByStreamId(streamId);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    // Add DELETE endpoints, etc., if needed for unassigning subjects
}


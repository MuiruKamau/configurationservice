package com.school.configurationservice.controller;

import com.school.configurationservice.dto.StreamRequestDTO;
import com.school.configurationservice.dto.StreamResponseDTO;
import com.school.configurationservice.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/config/streams")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StreamResponseDTO> createStream(@RequestBody StreamRequestDTO streamRequestDTO) {
        try {
            StreamResponseDTO createdStream = streamService.createStream(streamRequestDTO);
            return new ResponseEntity<>(createdStream, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or handle specific error responses
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')")
    public ResponseEntity<StreamResponseDTO> getStreamById(@PathVariable Long id) {
        StreamResponseDTO stream = streamService.getStreamById(id);
        if (stream != null) {
            return new ResponseEntity<>(stream, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER', 'ADMINISTRATOR')")
    public ResponseEntity<List<StreamResponseDTO>> getAllStreams() {
        List<StreamResponseDTO> streams = streamService.getAllStreams();
        return new ResponseEntity<>(streams, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StreamResponseDTO> updateStream(@PathVariable Long id, @RequestBody StreamRequestDTO streamRequestDTO) {
        try {
            StreamResponseDTO updatedStream = streamService.updateStream(id, streamRequestDTO);
            if (updatedStream != null) {
                return new ResponseEntity<>(updatedStream, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or handle specific error responses
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteStream(@PathVariable Long id) {
        if (streamService.deleteStream(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

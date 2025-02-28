package com.school.configurationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "stream_subjects")
public class StreamSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stream_id", nullable = false)
    private Stream stream;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private LearningSubject learningSubject;

    public StreamSubject(Stream stream, LearningSubject learningSubject) {
        this.stream = stream;
        this.learningSubject = learningSubject;
    }
}

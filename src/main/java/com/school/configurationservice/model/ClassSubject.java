package com.school.configurationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "class_subjects")
public class ClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private LearningSubject learningSubject;

    public ClassSubject(SchoolClass schoolClass, LearningSubject learningSubject) {
        this.schoolClass = schoolClass;
        this.learningSubject = learningSubject;
    }
}

package com.school.configurationservice.model;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "learning_subjects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LearningSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g., "Physics", "Maths", "English", "Chemistry"
}
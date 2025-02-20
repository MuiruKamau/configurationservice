package com.school.configurationservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grade_criteria")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int minScore;

    @Column(nullable = false)
    private int maxScore;

    @Column(nullable = false)
    private String grade;  // e.g., "E", "D-", "A"

    @Column(nullable = false)
    private int points;  // e.g., 1, 2, 12
}
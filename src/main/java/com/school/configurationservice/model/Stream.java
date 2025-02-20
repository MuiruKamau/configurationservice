package com.school.configurationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "streams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // e.g., "East", "West", "North"

    @ManyToOne
    @JoinColumn(name = "school_class_id", nullable = false)
    @JsonIgnore
    private SchoolClass schoolClass;
}
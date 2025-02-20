package com.school.configurationservice.dto;

import lombok.Data;

@Data
public class GradeCriteriaRequestDTO {
    private int minScore;
    private int maxScore;
    private String grade;
    private int points;
}

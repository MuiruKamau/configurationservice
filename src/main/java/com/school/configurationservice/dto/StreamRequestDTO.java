package com.school.configurationservice.dto;

import lombok.Data;

@Data
public class StreamRequestDTO {
    private String name;
    private Long schoolClassId; // Using schoolClassId for request
}

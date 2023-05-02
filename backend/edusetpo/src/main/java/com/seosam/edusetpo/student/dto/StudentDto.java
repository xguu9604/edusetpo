package com.seosam.edusetpo.student.dto;

import lombok.Builder;
import lombok.Data;

/**
 * A DTO for the {@link com.seosam.edusetpo.student.entity.Student} entity
 */
@Data
@Builder
public class StudentDto {
    private final Long tutorId;
    private final String studentName;
    private final String studentContact;
    private final String parentContact;
    private final Boolean isActive;
}
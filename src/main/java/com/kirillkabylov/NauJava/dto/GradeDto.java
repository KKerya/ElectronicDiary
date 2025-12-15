package com.kirillkabylov.NauJava.dto;

import java.time.LocalDate;

public record GradeDto(
        String studentFullName,
        String teacherFullName,
        Integer value,
        LocalDate date
) {
}
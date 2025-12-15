package com.kirillkabylov.NauJava.dto;

import java.time.LocalDate;

public record GradeCreateRequest(
        Long studentId,
        Long subjectId,
        int value,
        LocalDate date
) {
}

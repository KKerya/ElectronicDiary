package com.kirillkabylov.NauJava.dto;

import java.time.LocalDate;

public record AttendanceDto(
        LocalDate date,
        StudentDto student,
        String status
) {
}

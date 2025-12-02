package com.kirillkabylov.NauJava.dto;

import com.kirillkabylov.NauJava.enums.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceDto(
        LocalDate date,
        StudentDto student,
        String status
) {
}

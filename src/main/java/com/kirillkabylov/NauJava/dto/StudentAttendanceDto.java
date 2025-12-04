package com.kirillkabylov.NauJava.dto;

public record StudentAttendanceDto(
        Long id,
        String fullName,
        String status
){}
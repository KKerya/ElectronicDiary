package com.kirillkabylov.NauJava.dto;

public record AttendanceCreateRequest(
        Long studentId,
        Long lessonId,
        String status
) {}

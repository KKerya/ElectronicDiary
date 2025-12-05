package com.kirillkabylov.NauJava.dto;

public record LessonForScheduleDto(
        String subjectName,
        String startTime,
        Integer durationMinutes
) {}

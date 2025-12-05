package com.kirillkabylov.NauJava.dto;

import java.time.LocalDateTime;

public record LessonDto(Long id, LocalDateTime startTime, Long subjectId, Integer durationMinutes) {}

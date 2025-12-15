package com.kirillkabylov.NauJava.dto;

import java.time.LocalDateTime;

public record CreateLessonRequest(
        Long groupId,
        Long subjectId,
        Long teacherId,
        LocalDateTime startTime,
        boolean wholeYear
) {
}
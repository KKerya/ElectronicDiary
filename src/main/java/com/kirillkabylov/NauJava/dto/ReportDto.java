package com.kirillkabylov.NauJava.dto;

import com.kirillkabylov.NauJava.domain.Student;

import java.util.List;

public record ReportDto(
        long userCount,
        List<Student> students,
        long timeUsers,
        long timeEntities,
        long totalTime
) {
}

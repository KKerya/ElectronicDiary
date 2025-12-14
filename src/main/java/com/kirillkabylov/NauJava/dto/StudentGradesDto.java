package com.kirillkabylov.NauJava.dto;

import java.util.Map;

public record StudentGradesDto(
        Long studentId,
        String fullName,
        Map<Integer, Integer> grades
) {
}
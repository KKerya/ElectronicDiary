package com.kirillkabylov.NauJava.dto;

import java.time.LocalDateTime;

public record GradeDto(
        String studentFullName,
        String teacherFullName,
        Integer value,
        LocalDateTime date
) {}
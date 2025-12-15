package com.kirillkabylov.NauJava.dto;

import java.time.LocalDateTime;

public record HomeworkDto (
        Long id,
        String description,
        LocalDateTime createdAt,
        LocalDateTime deadline
){
}

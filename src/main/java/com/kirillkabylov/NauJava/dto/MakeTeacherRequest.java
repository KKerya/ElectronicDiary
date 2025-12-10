package com.kirillkabylov.NauJava.dto;

import java.util.List;

public record MakeTeacherRequest(
        String login,
        List<Long> subjectIds
) {}
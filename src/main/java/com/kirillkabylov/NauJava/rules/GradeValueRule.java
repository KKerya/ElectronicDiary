package com.kirillkabylov.NauJava.rules;

import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;

public class GradeValueRule implements GradeRule {
    private final int maxScore;
    private final int minScore;

    @Autowired
    public GradeValueRule(int maxScore, int minScore) {
        this.maxScore = maxScore;
        this.minScore = minScore;
    }

    @Override
    public void validate(Grade grade) {
        int value = grade.getValue();
        if (value < minScore || value > maxScore) {
            throw new IllegalArgumentException("Некорректное значение оценки");
        }
    }
}
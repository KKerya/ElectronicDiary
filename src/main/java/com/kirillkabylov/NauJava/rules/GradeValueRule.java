package com.kirillkabylov.NauJava.rules;

import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Правило проверки корректности значения оценки.
 * Валидирует объект {@link Grade}, проверяя, что значение оценки
 * находится в допустимом диапазоне между {@code minScore} и {@code maxScore}.
 */
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
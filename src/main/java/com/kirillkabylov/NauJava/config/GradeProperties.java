package com.kirillkabylov.NauJava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GradeProperties {

    @Value("${grade.max-score}")
    private int maxScore;

    @Value("${grade.min-score}")
    private int minScore;

    public int getMaxScore() {
        return maxScore;
    }

    public int getMinScore() {
        return minScore;
    }
}

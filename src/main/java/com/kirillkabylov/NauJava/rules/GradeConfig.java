package com.kirillkabylov.NauJava.rules;

import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурационный класс Spring для регистрации правил проверки оценок.
 * Содержит настройки допустимого диапазона оценок и создает список
 * всех правил валидации, применяемых к объектам {@link Grade}
 */
@Configuration
public class GradeConfig {
    @Value("${grade.max-score:5}")
    private int maxScore;
    @Value("${grade.min-score:2}")
    private int minScore;

    @Bean
    public List<GradeRule> gradeRules() {
        return List.of(
                new GradeValueRule(maxScore, minScore),
                new GradeNotNullRule()
        );
    }
}
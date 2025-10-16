package com.kirillkabylov.NauJava.rules;

import com.kirillkabylov.NauJava.domain.Grade;

public class GradeNotNullRule implements GradeRule {
    @Override
    public void validate(Grade grade) {
        if (grade.getStudent() == null) {
            throw new IllegalArgumentException("Значение студента не может быть null");
        }
        if (grade.getSubject() == null) {
            throw new IllegalArgumentException("Значение предмета не может быть null");
        }
        if (grade.getTeacher() == null) {
            throw new IllegalArgumentException("Значение учителя не может быть null");
        }
    }
}

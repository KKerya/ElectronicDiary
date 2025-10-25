package com.kirillkabylov.NauJava.rules;

import com.kirillkabylov.NauJava.domain.Grade;

/**
 *
 * Правило проверки на наличие обязательных ссылок в объекте {@link Grade}.
 * Гарантирует, что у оценки не равны null следующие поля:
 * student — студент, получивший оценку
 * subject — предмет, по которому выставлена оценка
 * teacher — преподаватель, поставивший оценку
 *
 */
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

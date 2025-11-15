package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;

public interface GradeService {
    /**
     * Создает оценку
     * @param student студент
     * @param value оценка
     * @param subject предмет
     * @param teacher учитель
     */
    void addGrade(Student student, int value, String subject, Teacher teacher, LocalDateTime dateTime);

    /**
     * Находит оценку студента
     * @param studentId id студента
     * @param subject предмет
     * @param value значение оценки
     * @param dateTime время
     * @return grade
     */
    Grade findGrade(long studentId, String subject, int value, LocalDateTime dateTime);

    /**
     * Находит оценку студента по id
     * @param id id
     */
    Grade findById(long id);

    /**
     * Удаляет оценку студента
     * @param studentId id студента
     * @param subject предмет
     * @param value значение оценки
     * @param dateTime время
     */
    void deleteGradeFromStudent(long studentId, String subject, int value, LocalDateTime dateTime);

    /**
     * Удаляет все оценки студента
     * @param student студент
     */
    void deleteAllGradesFromStudent(Student student);

    /**
     * Удаляет оценку студента
     * @param grade ссылка на оценку
     */
    void deleteGradeFromStudent(Grade grade);

    /**
     * Меняет оценку
     * @param grade    оценка
     * @param newValue новое значение оценки
     */
    void changeGradeValue(Grade grade, int newValue);
}

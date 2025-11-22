package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeService {
    /**
     * Создает оценку
     * @param student студент
     * @param value оценка
     * @param subject предмет
     * @param teacher учитель
     */
    void addGrade(Student student, int value, Subject subject, Teacher teacher, LocalDateTime dateTime);

    /**
     * Находит оценку студента
     * @param studentId id студента
     * @param subject предмет
     * @param value значение оценки
     * @param dateTime время
     * @return grade
     */
    Grade findGrade(long studentId, Subject subject, int value, LocalDateTime dateTime);

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
    void deleteGradeFromStudent(long studentId, Subject subject, int value, LocalDateTime dateTime);

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

    List<Grade> getGradesByStudentLoginAndSubject(String login, Long subjectId);

    List<Grade> getGradesBySubjectAndGroup(Long subjectId, Long groupId);
}

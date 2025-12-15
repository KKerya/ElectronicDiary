package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GradeService {
    /**
     * Создает оценку
     *
     * @param studentId id студента
     * @param value     оценка
     * @param subjectId id предмета
     * @param teacherId id учителя
     */
    Grade createGrade(Long studentId, int value, Long subjectId, Long teacherId, LocalDate date);

    /**
     * Находит оценку студента
     *
     * @param studentId id студента
     * @param subject   предмет
     * @param value     значение оценки
     * @param date  дата
     * @return grade
     */
    Grade getGrade(long studentId, Subject subject, int value, LocalDate date);

    /**
     * Находит оценку студента по id
     *
     * @param id id
     */
    Optional<Grade> findById(long id);

    /**
     * Удаляет оценку студента
     *
     * @param studentId id студента
     * @param subject   предмет
     * @param value     значение оценки
     * @param date  время
     */
    void deleteGradeFromStudent(long studentId, Subject subject, int value, LocalDate date);

    /**
     * Удаляет все оценки студента
     *
     * @param student студент
     */
    void deleteAllGradesFromStudent(Student student);

    /**
     * Удаляет оценку студента
     *
     * @param grade ссылка на оценку
     */
    void deleteGradeFromStudent(Grade grade);

    /**
     * Меняет оценку
     *
     * @param grade    оценка
     * @param newValue новое значение оценки
     */
    void changeGradeValue(Grade grade, int newValue);

    List<Grade> getGradesByStudentLoginAndSubject(String login, Long subjectId);

    List<Grade> getGradesBySubjectAndGroup(Long subjectId, Long groupId);

    List<Grade> getGradesByGroup(Long groupId);

    List<Grade> getGradesByStudent(String login);

    List<Grade> getGradesByGroupIdAndDateBetween(Long groupId, LocalDate start, LocalDate end);

    double getAverage(Long studentId, Long subjectId);

    double getAverage(String login, Long subjectId);

    double getAverage(Long teacherId, Long subjectId, Long groupId);

    long getGradesCount(Long subjectId, Long groupId);

    Map<Integer, Long> getGradeDistribution(Long subjectId, Long groupId);
}

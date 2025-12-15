package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация сервиса для управления оценками.
 * Сервис предоставляет операции для создания, удаления, нахождения и смены оценки
 * взаимодействуя с {@link GradeRepository} для выполнения операций с базой данных.
 */
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
     * @param date      дата
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
     * @param date      время
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

    /**
     * Получить оценки
     *
     * @param login     логин
     * @param subjectId id предмета
     */
    List<Grade> getGradesByStudentLoginAndSubject(String login, Long subjectId);

    /**
     * Получить оценки
     *
     * @param subjectId id предмета
     * @param groupId   id группы
     */
    List<Grade> getGradesBySubjectAndGroup(Long subjectId, Long groupId);

    /**
     * Получить оценки
     *
     * @param groupId id группы
     */
    List<Grade> getGradesByGroup(Long groupId);

    /**
     * Получить оценки
     *
     * @param login логин
     */
    List<Grade> getGradesByStudent(String login);

    /**
     * Получить оценки в интервал дат
     *
     * @param groupId id группы
     * @param start   начало интервала
     * @param end     конец интервала
     */
    List<Grade> getGradesByGroupIdAndDateBetween(Long groupId, LocalDate start, LocalDate end);

    /**
     * Среднее арифметическое оценок по предмету у студента
     *
     * @param studentId id студента
     * @param subjectId id предмета
     */
    double getAverage(Long studentId, Long subjectId);

    /**
     * Среднее арифметическое оценок по предмету у студента
     *
     * @param login     логин
     * @param subjectId id предмета
     */
    double getAverage(String login, Long subjectId);

    /**
     * Получить среднее арифметическое оценок
     *
     * @param teacherId id учителя
     * @param subjectId id предмета
     * @param groupId   id группы
     */
    double getAverage(Long teacherId, Long subjectId, Long groupId);

    /**
     * Количество оценок
     *
     * @param subjectId id предмета
     * @param groupId   id группы
     */
    long getGradesCount(Long subjectId, Long groupId);

    /**
     * Получить распределение оценок
     *
     * @param subjectId
     * @param groupId
     * @return
     */
    Map<Integer, Long> getGradeDistribution(Long subjectId, Long groupId);
}

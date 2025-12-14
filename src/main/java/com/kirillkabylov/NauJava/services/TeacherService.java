package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TeacherService {
    /**
     * Создание учителя
     *
     * @param login      логин
     * @param fullName   полное имя (ФИО)
     * @param password   пароль
     * @param subjectIds id предметов
     */
    Teacher createTeacher(String login, String fullName, String password, List<Long> subjectIds);

    @Transactional
    void promoteToTeacher(UserEntity user, List<Long> subjects);

    Teacher createTeacherWithoutEncodingPassword(String login, String fullName, String password, List<Long> subjectIds);

    /**
     * Находит учителя по id
     *
     * @param id id
     */
    Teacher getById(Long id);

    /**
     * Находит учителя по логину
     *
     * @param login
     */
    Teacher getByLogin(String login);

    /**
     * Удаляет учителя по id
     *
     * @param id id
     */
    void deleteTeacher(Long id);

    /**
     * Удаляет учителя
     *
     * @param teacher учитель
     */
    void deleteTeacher(Teacher teacher);

    /**
     * Обновляет поле учителя
     *
     * @param id       id
     * @param field    поле которое обновляем
     * @param newValue новое значение поля
     */
    void updateTeacher(Long id, String field, Object newValue);

    /**
     * Добавляет оценку
     *
     * @param value     значение оценки
     * @param studentId id студента
     * @param subjectId id предмета
     * @param teacherId id учителя
     * @param date дата
     */
    Grade createGrade(Long studentId, int value, Long subjectId, Long teacherId, LocalDate date);

    /**
     * Удалить оценку
     *
     * @param grade оценка
     */
    void deleteGrade(Grade grade);

    /**
     * Создает занятие
     *
     * @param group     класс
     * @param subject   предмет
     * @param teacher   учитель
     * @param startTime время начала
     * @param room      кабинет
     */
    void addLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime, String room);

    List<Teacher> getAllTeacher();
}

package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;

public interface TeacherService {
    /**
     * Создание учителя
     * @param login логин
     * @param fullName полное имя (ФИО)
     * @param password пароль
     * @param subject предмет
     */
    void createTeacher(String login, String fullName, String password, String subject);

    /**
     * Находит учителя по id
     * @param id id
     */
    Teacher findById(Long id);

    /**
     * Находит учителя по логину
     * @param login
     */
    Teacher findByLogin(String login);

    /**
     * Удаляет учителя по id
     * @param id id
     */
    void deleteTeacher(Long id);

    /**
     * Удаляет учителя
     * @param teacher учитель
     */
    void deleteTeacher(Teacher teacher);

    /**
     * Обновляет поле учителя
     * @param id id
     * @param field поле которое обновляем
     * @param newValue новое значение поля
     */
    void updateTeacher(Long id, String field, Object newValue);

    /**
     * Добавляет оценку
     * @param value значение оценки
     * @param student студент
     * @param subject предмет
     * @param teacher учитель
     * @param dateTime время
     */
    void addGrade(int value, Student student, String subject, Teacher teacher, LocalDateTime dateTime);

    /**
     * Удалить оценку
     * @param grade оценка
     */
    void deleteGrade(Grade grade);

    /**
     * Создает занятие
     * @param groupName класс
     * @param subject предмет
     * @param teacher учитель
     * @param startTime время начала
     * @param room кабинет
     */
    void addLesson(String groupName, String subject, Teacher teacher, LocalDateTime startTime, String room);
}

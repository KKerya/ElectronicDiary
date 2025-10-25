package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentService {
    /**
     * Удаляет студента и все его оценки
     * Transaction операция
     * @param student студент
     */
    @Transactional
    void deleteStudent(Student student);

    /**
     * Создать нового студента
     * @param login     логин
     * @param fullName  полное имя (ФИО)
     * @param password  пароль
     * @param group номер класса
     */
    void createStudent(String login, String fullName, String password, Group group);

    /**
     * Находит студента по id
     * @param id id
     */
    Optional<Student> findById(Long id);

    /**
     * Обновляет поле студента
     * @param id       id Студента
     * @param field    поле для обновление
     * @param newValue новое значение поля
     */
    void updateStudent(Long id, String field, Object newValue);
}

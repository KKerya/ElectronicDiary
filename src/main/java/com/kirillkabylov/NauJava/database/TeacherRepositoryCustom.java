package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;

import java.util.List;

public interface TeacherRepositoryCustom {
    /**
     * Находит всех учителей с заданным предметом
     * @param field поле по которому делаем поиск
     * @param value значение которое должно быть у поля
     */
    List<Teacher> findByField(String field, String value);
}

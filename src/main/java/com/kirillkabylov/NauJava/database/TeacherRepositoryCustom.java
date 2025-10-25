package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;

import java.util.List;

public interface TeacherRepositoryCustom {
    /**
     * Выполняет поиск всех преподавателей по значению указанного поля.
     * Использует Criteria API
     * @param field поле поиска
     * @param value значение поля
     */
    List<Teacher> findByField(String field, String value);
}

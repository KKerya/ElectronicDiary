package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.util.List;

/**
 * Сервис для взаимодействия с группами.
 * Содержит методы для создания, получения и поиска групп.
 */
public interface GroupService {

    /**
     * Получить все группы.
     *
     * @return список групп
     */
    List<Group> getAllGroups();

    /**
     * Получить группу по id.
     *
     * @param id id группы
     * @return группа
     */
    Group getById(Long id);

    /**
     * Создать новую группу с указанным именем.
     *
     * @param name название группы
     * @return созданная группа
     */
    Group createGroup(String name);

    /**
     * Создать новую группу с указанным именем и назначить преподавателя.
     *
     * @param name    название группы
     * @param teacher преподаватель
     * @return созданная группа
     */
    Group createGroup(String name, Teacher teacher);
}


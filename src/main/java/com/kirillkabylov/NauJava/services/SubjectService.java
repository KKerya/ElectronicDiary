package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Subject;

import java.util.List;

/**
 * Сервис для управления предметами.
 * Предоставляет методы для создания, получения и назначения предметов преподавателям.
 */
public interface SubjectService {

    /**
     * Получает список всех предметов.
     *
     * @return список предметов
     */
    List<Subject> getAllSubjects();

    /**
     * Получает предметы, закрепленные за преподавателем.
     *
     * @param login логин преподавателя
     * @return список предметов
     */
    List<Subject> getSubjectsByTeacherLogin(String login);

    /**
     * Получает предмет по его id.
     *
     * @param subjectId id предмета
     * @return предмет
     */
    Subject getById(Long subjectId);

    /**
     * Находит предметы по списку id.
     *
     * @param subjectsIds список id предметов
     * @return список предметов
     */
    List<Subject> findSubjectByIds(List<Long> subjectsIds);

    /**
     * Создает новый предмет.
     *
     * @param name название предмета
     * @return созданный предмет
     */
    Subject createSubject(String name);

    /**
     * Назначает преподавателя предмету.
     *
     * @param subjectId id предмета
     * @param teacherId id преподавателя
     */
    void setSubjectTeacher(Long subjectId, Long teacherId);

    /**
     * Удаляет предмет у преподавателя.
     *
     * @param subjectId id предмета
     * @param teacherId id преподавателя
     */
    void removeSubjectFromTeacher(Long subjectId, Long teacherId);
}

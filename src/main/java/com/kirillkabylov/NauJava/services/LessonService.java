package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для управления занятиями.
 * Содержит методы для создания и получения занятий по различным критериям.
 */
public interface LessonService {

    /**
     * Создает занятие для указанной группы, предмета и преподавателя.
     *
     * @param group     группа студентов
     * @param subject   предмет
     * @param teacher   преподаватель
     * @param startTime время начала занятия
     * @return созданное занятие
     */
    Lesson createLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime);

    /**
     * Создает занятие по id сущностей.
     * При необходимости может создать занятия на весь учебный год.
     *
     * @param groupId   id группы
     * @param subjectId id предмета
     * @param teacherId id преподавателя
     * @param startTime время начала занятия
     * @param wholeYear флаг создания занятий на весь учебный год
     * @return созданное занятие
     */
    Lesson createLesson(Long groupId, Long subjectId, Long teacherId, LocalDateTime startTime, boolean wholeYear);

    /**
     * Получает список занятий по логину преподавателя.
     *
     * @param login логин преподавателя
     * @return список занятий
     */
    List<Lesson> getLessonsByTeacherLogin(String login);

    /**
     * Получает занятие по его id.
     *
     * @param lessonId id занятия
     * @return занятие
     */
    Lesson getLessonById(Long lessonId);

    /**
     * Получает занятия группы за указанный период времени.
     *
     * @param groupId   id группы
     * @param startDate дата начала периода
     * @param endDate   дата окончания периода
     * @return список занятий
     */
    List<Lesson> getLessonsByGroupIdBetweenDates(Long groupId, LocalDate startDate, LocalDate endDate);

    /**
     * Получает занятия преподавателя по предмету и группе.
     *
     * @param teacherId id преподавателя
     * @param subjectId id предмета
     * @param groupId   id группы
     * @return список занятий
     */
    List<Lesson> getLessonForTeacher(Long teacherId, Long subjectId, Long groupId);
}

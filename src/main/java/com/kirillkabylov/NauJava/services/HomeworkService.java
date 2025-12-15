package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Homework;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с домашними заданиями.
 * Отвечает за создание домашних заданий и получение их для студентов.
 */
public interface HomeworkService {

    /**
     * Создает или обновляет домашнее задание для конкретного занятия.
     *
     * @param description описание домашнего задания
     * @param lessonId    id занятия
     * @param deadline    крайний срок сдачи домашнего задания
     * @return созданное или обновленное домашнее задание
     */
    Homework createHomework(String description, Long lessonId, LocalDateTime deadline);

    /**
     * Получает список домашних заданий для студента по конкретному предмету.
     *
     * @param studentId id студента
     * @param subjectId id предмета
     * @return список домашних заданий
     */
    List<Homework> getHomeworkForStudentBySubject(Long studentId, Long subjectId);
}

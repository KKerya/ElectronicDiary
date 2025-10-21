package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;

import java.util.List;

public interface LessonRepositoryCustom {
    /**
     * Находит все занятия данного учителя
     * Использует Criteria API
     * @param fullName имя учителя
     */
    List<Lesson> findLessonByTeacherName(String fullName);
}

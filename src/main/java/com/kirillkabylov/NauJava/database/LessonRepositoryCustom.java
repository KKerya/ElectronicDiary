package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;

import java.util.List;

public interface LessonRepositoryCustom {
    List<Lesson> findLessonByTeacherName(String fullName);
}

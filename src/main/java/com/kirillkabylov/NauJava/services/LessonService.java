package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getLessonsByTeacherLogin(String login);

    Lesson getLessonById(Long lessonId);
}

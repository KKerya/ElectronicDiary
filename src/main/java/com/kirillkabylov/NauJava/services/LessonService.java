package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {
    Lesson createLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime);

    Lesson createLesson(Long groupId, Long subjectId, Long teacherId, LocalDateTime startTime, boolean wholeYear);

    List<Lesson> getLessonsByTeacherLogin(String login);

    Lesson getLessonById(Long lessonId);

    List<Lesson> getLessonsByGroupIdBetweenDates(Long groupId, LocalDate startDate, LocalDate endDate);
}

package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Homework;

import java.time.LocalDateTime;
import java.util.List;

public interface HomeworkService {
    Homework createHomework(String description, Long lessonId, LocalDateTime deadline);

    List<Homework> getHomeworkForStudentBySubject(Long studentId, Long subjectId);
}

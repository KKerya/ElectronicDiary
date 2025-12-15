package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.HomeworkRepository;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Homework;
import com.kirillkabylov.NauJava.domain.Lesson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, LessonRepository lessonRepository) {
        this.homeworkRepository = homeworkRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Homework createHomework(String description, Long lessonId, LocalDateTime deadline) {
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Некорректное значение дедлайна");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson with id - " + lessonId + " not found"));
        Homework homework = new Homework(lesson, deadline, LocalDateTime.now(), description);
        lesson.setHomework(homework);
        lessonRepository.save(lesson);
        return homework;
    }

    @Override
    public List<Homework> getHomeworkForStudentBySubject(Long studentId, Long subjectId) {
        return homeworkRepository.findByLessonGroupStudentsIdAndLessonSubjectId(studentId, subjectId);
    }
}

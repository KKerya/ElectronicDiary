package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;
    @Value("${lesson.max-duration:45}")
    private int defaultDuration;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Lesson createLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime) {
        Lesson lesson = new Lesson(group, subject, teacher, startTime);
        lesson.setDurationMinutes(defaultDuration);
        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getLessonsByTeacherLogin(String login){
        return lessonRepository.findByTeacherLogin(login);
    }

    @Override
    public Lesson getLessonById(Long lessonId){
        return lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson with id - " + lessonId + " not found"));
    }

    @Override
    public List<Lesson> getLessonsByGroupIdBetweenDates(Long groupId, LocalDate startDate, LocalDate endDate){
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return lessonRepository.findByGroupIdAndStartTimeBetween(groupId, startDateTime, endDateTime);
    }
}

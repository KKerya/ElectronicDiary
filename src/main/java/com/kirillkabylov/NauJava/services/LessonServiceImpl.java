package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;


    @Value("${lesson.max-duration:45}")
    private int defaultDuration;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, GroupRepository groupRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.lessonRepository = lessonRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Lesson createLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime) {
        Lesson lesson = new Lesson(group, subject, teacher, startTime);
        lesson.setDurationMinutes(defaultDuration);
        return lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public Lesson createLesson(Long groupId, Long subjectId, Long teacherId, LocalDateTime startTime, boolean wholeYear) {
        Group group = groupRepository.getReferenceById(groupId);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        Teacher teacher = teacherRepository.getReferenceById(teacherId);

        Lesson lesson = new Lesson(group, subject, teacher, startTime);
        lesson.setDurationMinutes(defaultDuration);
        lesson = lessonRepository.save(lesson);

        // создание на весь учебный год — 34 недели
        if (wholeYear) {
            for (int i = 1; i < 34; i++) {
                Lesson dublicateLesson = new Lesson(group, subject, teacher, startTime.plusWeeks(i));
                dublicateLesson.setDurationMinutes(defaultDuration);
                lessonRepository.save(dublicateLesson);
            }
        }

        return lesson;
    }

    @Override
    public List<Lesson> getLessonsByTeacherLogin(String login) {
        return lessonRepository.findByTeacherLogin(login);
    }

    @Override
    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson with id - " + lessonId + " not found"));
    }

    @Override
    public List<Lesson> getLessonsByGroupIdBetweenDates(Long groupId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return lessonRepository.findByGroupIdAndStartTimeBetween(groupId, startDateTime, endDateTime);
    }

}

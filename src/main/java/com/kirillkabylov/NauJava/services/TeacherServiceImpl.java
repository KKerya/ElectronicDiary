package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.command.UserUpdateCommand;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Реализация сервиса для управления учителями.
 * Сервис предоставляет операции для создания, удаления, обновления и нахождения учителя, добавление оценки и создание занятия
 * взаимодействуя с {@link LessonRepository} для выполнения операций с базой данных.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final GradeService gradeService;
    private final Map<String, UserUpdateCommand<Teacher>> commands;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              LessonRepository lessonRepository,
                              GradeService gradeService,
                              Map<String, UserUpdateCommand<Teacher>> commands) {
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.gradeService = gradeService;
        this.commands = commands;
    }

    @Override
    public Teacher createTeacher(String login, String fullName, String password, String subject) {
        if (teacherRepository.findByLogin(login).isPresent()){
            throw new RuntimeException("Учитель с таким логином уже существует");
        }
        Teacher newTeacher = new Teacher(login, fullName, password, subject);
        teacherRepository.save(newTeacher);
        return newTeacher;
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Teacher findByLogin(String login){
        return teacherRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.delete(findById(id));
    }

    @Override
    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    public void updateTeacher (Long id, String field, Object newValue) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserUpdateCommand<Teacher> command = commands.get(field);
        if (command == null) {
            throw new IllegalArgumentException("Неизвестное поле: " + field);
        }
        command.execute(teacher, newValue);
        teacherRepository.save(teacher);
    }

    @Override
    public void addGrade(int value, Student student, String subject, Teacher teacher, LocalDateTime dateTime) {
        gradeService.addGrade(student, value, subject, teacher, dateTime);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeService.deleteGradeFromStudent(grade);
    }

    @Override
    public void addLesson(String groupName, String subject, Teacher teacher, LocalDateTime startTime, String room) {
        lessonRepository.save(new Lesson(groupName, subject, teacher, startTime));
    }
}
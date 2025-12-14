package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.command.UserUpdateCommand;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final PasswordEncoder passwordEncoder;
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              LessonRepository lessonRepository,
                              GradeService gradeService,
                              Map<String, UserUpdateCommand<Teacher>> commands,
                              PasswordEncoder passwordEncoder,
                              SubjectRepository subjectRepository,
                              UserService userService) {
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.gradeService = gradeService;
        this.commands = commands;
        this.passwordEncoder = passwordEncoder;
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Teacher createTeacher(String login, String fullName, String password, List<Long> subjectIds) {
        if (teacherRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("Учитель с таким логином уже существует");
        }
        Teacher teacher = new Teacher(login, fullName, passwordEncoder.encode(password));
        teacherRepository.save(teacher);

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        for (Subject subject : subjects) {
            subject.getTeachers().add(teacher);
        }

        teacher.setSubjects(subjects);

        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void promoteToTeacher(UserEntity user, List<Long> subjectIds) {
        userService.deleteUser(user);
        createTeacherWithoutEncodingPassword(user.getLogin(), user.getFullName(), user.getPassword(), subjectIds);
    }

    @Override
    @Transactional
    public Teacher createTeacherWithoutEncodingPassword(String login, String fullName, String password, List<Long> subjectIds) {
        if (teacherRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("Учитель с таким логином уже существует");
        }
        Teacher teacher = new Teacher(login, fullName, password);
        teacherRepository.save(teacher);

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        for (Subject subject : subjects) {
            subject.getTeachers().add(teacher);
        }

        teacher.setSubjects(subjects);

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher with id - " + id + " not found"));
    }

    @Override
    public Teacher getByLogin(String login) {
        return teacherRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Group with login - " + login + " not found"));
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.delete(getById(id));
    }

    @Override
    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    public void updateTeacher(Long id, String field, Object newValue) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group with id - " + id + " not found"));
        UserUpdateCommand<Teacher> command = commands.get(field);
        if (command == null) {
            throw new IllegalArgumentException("Неизвестное поле: " + field);
        }
        command.execute(teacher, newValue);
        teacherRepository.save(teacher);
    }

    @Override
    public Grade createGrade(Long studentId, int value, Long subjectId, Long teacherId, LocalDateTime dateTime) {
        return gradeService.createGrade(studentId, value, subjectId, teacherId, dateTime);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeService.deleteGradeFromStudent(grade);
    }

    @Override
    public void addLesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime, String room) {
        lessonRepository.save(new Lesson(group, subject, teacher, startTime));
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }
}
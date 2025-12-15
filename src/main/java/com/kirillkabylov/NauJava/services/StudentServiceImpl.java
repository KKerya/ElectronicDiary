package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.command.UserUpdateCommand;
import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Map;

/**
 * Реализация сервиса для управления cтудентами.
 * Сервис предоставляет операции для создания, удаления, обновления и нахождения студента
 * взаимодействуя с {@link StudentRepository} для выполнения операций с базой данных.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GradeService gradeService;
    private final PlatformTransactionManager transactionManager;
    private final Map<String, UserUpdateCommand<Student>> commands;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final UserService userService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GradeService gradeService,
                              LessonRepository lessonRepository,
                              PlatformTransactionManager transactionManager,
                              Map<String, UserUpdateCommand<Student>> commands,
                              PasswordEncoder passwordEncoder, GroupRepository groupRepository, LessonRepository lessonRepository1, UserService userService) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.transactionManager = transactionManager;
        this.commands = commands;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository1;
        this.userService = userService;
    }

    @Override
    public void deleteStudent(Student student) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            gradeService.deleteAllGradesFromStudent(student);
            studentRepository.delete(student);
            transactionManager.commit(status);
        } catch (DataAccessException ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    @Transactional
    public void promoteToStudent(UserEntity user, Long groupId) {
        userService.deleteUser(user);
        createStudentWithoutEncodingPassword(user.getLogin(), user.getFullName(), user.getPassword(), groupId);
    }

    @Override
    @Transactional
    public Student createStudentWithoutEncodingPassword(String login, String fullName, String password, Long groupId) {
        if (studentRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("Студент с таким логином уже существует");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id - " + groupId + " not found"));
        Student student = new Student(login, fullName, password, group);
        studentRepository.save(student);

        group.getStudents().add(student);
        groupRepository.save(group);
        return student;
    }

    @Override
    public Student createStudent(String login, String fullName, String password, Group group) {
        if (studentRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("Студент с таким логином уже существует");
        }
        Student newStudent = new Student(login, fullName, passwordEncoder.encode(password), group);
        return studentRepository.save(newStudent);
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id - " + id + " not found"));
    }

    @Override
    public void updateStudent(Long id, String field, Object newValue) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id - " + id + " not found"));
        UserUpdateCommand<Student> command = commands.get(field);
        if (command == null) {
            throw new IllegalArgumentException("Неизвестное поле: " + field);
        }
        command.execute(student, newValue);
        studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByGroupId(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id - " + groupId + " not found")).getStudents();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getByLogin(String login) {
        return studentRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Student with login - " + login + " not found"));
    }

    @Override
    public List<Student> getStudentsByLesson(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson with id - " + lessonId + " not found")).getGroup().getStudents();
    }
}
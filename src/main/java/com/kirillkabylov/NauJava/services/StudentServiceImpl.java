package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.command.UserUpdateCommand;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Map;
import java.util.Optional;

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
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GradeService gradeService,
                              LessonRepository lessonRepository,
                              PlatformTransactionManager transactionManager,
                              Map<String, UserUpdateCommand<Student>> commands) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.transactionManager = transactionManager;
        this.commands = commands;
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
    public void createStudent(String login, String fullName, String password, Group group) {
        Student newStudent = new Student(login, fullName, password, group);
        studentRepository.save(newStudent);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void updateStudent(Long id, String field, Object newValue) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserUpdateCommand<Student> command = commands.get(field);
        if (command == null) {
            throw new IllegalArgumentException("Неизвестное поле: " + field);
        }
        command.execute(student, newValue);
        studentRepository.save(student);
    }
}
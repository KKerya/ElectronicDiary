package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GradeService gradeService;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GradeService gradeService, LessonRepository lessonRepository, PlatformTransactionManager transactionManager) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.transactionManager = transactionManager;
    }

    /**
     * Удаляет студента и все его оценки
     * Transaction операция
     *
     * @param student студент
     */
    @Override
    public void deleteStudent(Student student) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            gradeService.deleteAllGrades(student);
            studentRepository.delete(student);
            transactionManager.commit(status);
        } catch (DataAccessException ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    /**
     * Создать нового студента
     *
     * @param login     логин
     * @param fullName  полное имя (ФИО)
     * @param password  пароль
     * @param groupName номер класса
     */
    @Override
    public void createStudent(String login, String fullName, String password, String groupName) {
        Student newStudent = new Student(login, fullName, password, groupName);
        studentRepository.save(newStudent);
    }

    /**
     * Находит студента по id
     *
     * @param id id
     */
    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * Обновляет поле студента
     *
     * @param id       id Студента
     * @param field    поле для обновление
     * @param newValue новое значение поля
     */
    @Override
    public void updateStudent(Long id, String field, String newValue) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        switch (field) {
            case "login" -> student.get().setLogin(newValue);
            case "password" -> student.get().setPassword(newValue);
            case "fullName" -> student.get().setFullName(newValue);
            case "groupName" -> student.get().setGroupName(newValue);
            default -> {
                throw new IllegalArgumentException("Неизвестное поле: " + field);
            }
        }

        studentRepository.save(student.get());
    }
}
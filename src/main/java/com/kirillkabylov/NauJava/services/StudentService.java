package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentService {
    @Transactional
    void deleteStudent(Student student);

    void createStudent(String login, String fullName, String password, String groupName);

    Optional<Student> findById(Long id);

    void updateStudent(Long id, String field, String newValue);
}

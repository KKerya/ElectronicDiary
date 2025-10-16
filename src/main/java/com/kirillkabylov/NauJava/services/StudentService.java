package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Student;

import java.util.Optional;

public interface StudentService {
    void createStudent(Long id, String login, String fullName, String password, String groupName);

    Optional<Student> findById(Long id);

    void deleteById(Long id);

    void updateStudent(Long id, String field, String newValue);

    void printAllStudents();

    void printGrades(Long studentId, String subject);

    void printAllGrades(Long studentId);

    void checkSchedule(Long studentId);
}

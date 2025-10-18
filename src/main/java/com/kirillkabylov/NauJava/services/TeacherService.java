package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;

public interface TeacherService {
    void createTeacher(Long id, String login, String fullName, String password, String subject);

    Teacher findById(Long id);

    void deleteById(Long id);

    void updateTeacher(Long id, String field, String newValue);

    void addGrade(Long id, Long studentId, String subject, int value, Long teacherId);

    void addGrade(Long id, int value, Student student, String subject, Teacher teacher);

    void deleteGrade(Long studentId, String subject, int value, LocalDateTime dateTime);

    void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime);

    void deleteGrade(Long gradeId);

    void deleteGrade(Grade grade);

    void printAllTeachers();
}

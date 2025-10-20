package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;

public interface GradeService {
    void addGrade(Student student, int value, String subject, Teacher teacher);

    void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime);

    void deleteAllGrades(Student student);

    void deleteGrade(Grade grade);

    void changeGrade(Grade grade, int newValue);
}

package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import java.time.LocalDateTime;

public interface GradeService {
    void addGrade(Long id,Long studentId, String subject, int value, Long teacherId);

    void addGrade(Long id, int value, Student student, String subject, Teacher teacher);

    void deleteGrade(Long studentId, String subject, int value, LocalDateTime dateTime);

    void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime);

    void deleteGrade(Long gradeId);

    void deleteGrade(Grade grade);

    void changeGrade(Long studentId, String subject, int value, LocalDateTime dateTime, int newValue);

    void changeGrade(Student student, String subject, int value, LocalDateTime dateTime, int newValue);

    void changeGrade(Long gradeId, int newValue);

    void changeGrade(Grade grade, int newValue);

    void printGradesByStudent(Long studentId, String subject);

    void printAllGradesByStudent(Long studentId);
}

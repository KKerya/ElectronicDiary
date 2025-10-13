package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

public interface TeacherService {
    void createTeacher(Long id, String login, String fullName, String password, Subject subject);

    Teacher findById(Long id);

    void deleteById(Long id);

    void updateLogin(Long id, String newLogin);

    void updatePassword(Long id, String newPassword);

    void updateFullName(Long id, String newName);

    void updateSubject(Long id, String newSubjectName);

    void addGrade(Student student, Subject subject, int value, Teacher teacher);

    void deleteGrade(Student student, Subject subject, int value, Teacher teacher);

    void deleteGrade(Grade grade);
}

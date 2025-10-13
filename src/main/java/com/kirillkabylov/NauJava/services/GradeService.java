package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

public interface GradeService {
    void addGrade(Student student, Subject subject, int value, Teacher teacher);

    void deleteGrade(Student student, Subject subject, int value, Teacher teacher);

    void deleteGrade(Grade grade);
}

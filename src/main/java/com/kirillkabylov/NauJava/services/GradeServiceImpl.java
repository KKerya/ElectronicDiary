package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

import java.time.LocalDateTime;

public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void addGrade(Student student, Subject subject, int value, Teacher teacher){
        gradeRepository.create(new Grade(student, subject, teacher, value, LocalDateTime.now()));
    }

    @Override
    public void deleteGrade(Student student, Subject subject, int value, Teacher teacher){
        Grade grade = gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().equals(student) &&
                        g.getSubject().equals(subject) &&
                        g.getValue() == value &&
                        g.getTeacher().equals(teacher))
                .findFirst()
                .orElse(null);
        if (grade != null){
            gradeRepository.delete(grade.getId());
        }
    }

    @Override
    public void deleteGrade(Grade grade){
        gradeRepository.delete(grade.getId());
    }


}

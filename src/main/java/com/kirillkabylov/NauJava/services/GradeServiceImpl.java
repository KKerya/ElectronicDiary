package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.rules.GradeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final List<GradeRule> gradeRules;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, List<GradeRule> gradeRules, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeRules = gradeRules;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void addGrade(Long id, Long studentId, String subject, int value, Long teacherId) {
        Grade grade = new Grade(id, value, studentRepository.read(studentId), subject, teacherRepository.read(teacherId), LocalDateTime.now());
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        gradeRepository.create(grade);
    }

    @Override
    public void addGrade(Long id, int value, Student student, String subject, Teacher teacher) {
        Grade grade = new Grade(id, value, student, subject, teacher, LocalDateTime.now());
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        gradeRepository.create(grade);
    }

    @Override
    public void deleteGrade(Long studentId, String subject, int value, LocalDateTime dateTime) {
        Grade grade = gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().getId().equals(studentId) &&
                        g.getSubject().equals(subject) &&
                        g.getValue() == value &&
                        g.getDate().equals(dateTime))
                .findFirst()
                .orElse(null);
        if (grade != null) {
            gradeRepository.delete(grade.getId());
        }
    }

    @Override
    public void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime) {
        Grade grade = gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().equals(student) &&
                        g.getSubject().equals(subject) &&
                        g.getValue() == value &&
                        g.getDate().equals(dateTime))
                .findFirst()
                .orElse(null);
        if (grade != null) {
            gradeRepository.delete(grade.getId());
        }
    }

    @Override
    public void deleteGrade(Long gradeId) {
        gradeRepository.delete(gradeId);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeRepository.delete(grade.getId());
    }

    @Override
    public void changeGrade(Long studentId, String subject, int value, LocalDateTime dateTime, int newValue) {
        Grade grade = gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().getId().equals(studentId) &&
                        g.getSubject().equals(subject) &&
                        g.getValue() == value &&
                        g.getDate().equals(dateTime))
                .findFirst()
                .orElse(null);
        if (grade != null) {
            for (GradeRule rule : gradeRules) {
                rule.validate(grade);
            }
            grade.setValue(newValue);
            gradeRepository.update(grade);
        }
    }

    @Override
    public void changeGrade(Student student, String subject, int value, LocalDateTime dateTime, int newValue) {
        Grade grade = gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().equals(student) &&
                        g.getSubject().equals(subject) &&
                        g.getValue() == value &&
                        g.getDate().equals(dateTime))
                .findFirst()
                .orElse(null);
        if (grade != null) {
            for (GradeRule rule : gradeRules) {
                rule.validate(grade);
            }
            grade.setValue(newValue);
            gradeRepository.update(grade);
        }
    }

    @Override
    public void changeGrade(Long gradeId, int newValue) {
        Grade grade = gradeRepository.read(gradeId);
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        grade.setValue(newValue);
        gradeRepository.update(grade);
    }

    @Override
    public void changeGrade(Grade grade, int newValue) {
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        grade.setValue(newValue);
        gradeRepository.update(grade);
    }

    @Override
    public void printGradesByStudent(Long studentId, String subject) {
        gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().getId().equals(studentId) &&
                        g.getSubject().equals(subject))
                .forEach(System.out::println);
    }

    @Override
    public void printAllGradesByStudent(Long studentId) {
        gradeRepository.findAll().stream()
                .filter(g -> g.getStudent().getId().equals(studentId))
                .forEach(System.out::println);
    }
}
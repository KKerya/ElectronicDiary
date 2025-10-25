package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.GradeNotFoundException;
import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.rules.GradeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Реализация сервиса для управления оценками.
 * Сервис предоставляет операции для создания, удаления, нахождения и смены оценки
 * взаимодействуя с {@link GradeRepository} для выполнения операций с базой данных.
 */
@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final List<GradeRule> gradeRules;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, List<GradeRule> gradeRules, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeRules = gradeRules;
    }


    @Override
    public void addGrade(Student student, int value, String subject, Teacher teacher, LocalDateTime dateTime) {
        Grade grade = new Grade(value, student, subject, teacher, dateTime);
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        gradeRepository.save(grade);
    }

    @Override
    public Grade findGrade(long studentId, String subject, int value, LocalDateTime dateTime) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndSubjectAndValueAndDate(studentId, subject, value, dateTime);
        if (grade.isEmpty()) {
            throw new GradeNotFoundException();
        }
        return grade.get();
    }

    @Override
    public Grade findById(long id){
        return gradeRepository.findById(id).orElseThrow( () -> new UserNotFoundException(id));
    }

    @Override
    public void deleteGradeFromStudent(long studentId, String subject, int value, LocalDateTime dateTime) {
        gradeRepository.delete(findGrade(studentId, subject, value, dateTime));
    }

    @Override
    public void deleteAllGradesFromStudent(Student student) {
        gradeRepository.deleteAllByStudent(student);
    }

    @Override
    public void deleteGradeFromStudent(Grade grade) {
        gradeRepository.delete(grade);
    }

    @Override
    public void changeGradeValue(Grade grade, int newValue) {
        Optional<Grade> newGrade = gradeRepository.findById(grade.getId());
        if (newGrade.isEmpty()) {
            throw new GradeNotFoundException();
        }
        newGrade.get().setValue(newValue);
        gradeRepository.save(newGrade.get());
    }
}
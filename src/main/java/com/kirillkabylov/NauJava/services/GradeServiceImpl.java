package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.GradeNotFoundException;
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


@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final List<GradeRule> gradeRules;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, List<GradeRule> gradeRules, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeRules = gradeRules;
    }

    /**
     * Создает оценку
     * @param student студент
     * @param value оценка
     * @param subject предмет
     * @param teacher учитель
     */
    @Override
    public void addGrade(Student student, int value, String subject, Teacher teacher) {
        Grade grade = new Grade(value, student, subject, teacher, LocalDateTime.now());
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        gradeRepository.save(grade);
    }

    /**
     * Находит оценку студента
     * @param student студент
     * @param subject предмет
     * @param value значение оценки
     * @param dateTime время
     * @return grade
     */
    public Grade findGrade(Student student, String subject, int value, LocalDateTime dateTime){
        Optional<Grade> grade =  gradeRepository.findByStudentAndSubjectAndValueAndDate(student, subject, value, dateTime);
        if (grade.isEmpty()){
            throw new GradeNotFoundException();
        }
        return grade.get();
    }

    /**
     * Удаляет оценку студента
     * @param student студент
     * @param subject предмет
     * @param value значение оценки
     * @param dateTime время
     */
    @Override
    public void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime) {
        gradeRepository.delete(findGrade(student, subject, value, dateTime));
    }

    /**
     * Удаляет все оценки студента
     * @param student студент
     */
    @Override
    public void deleteAllGrades(Student student){
        gradeRepository.deleteAllByStudent(student);
    }

    /**
     * Удаляет оценку студента
     * @param grade ссылка на оценку
     */
    @Override
    public void deleteGrade(Grade grade) {
        gradeRepository.delete(grade);
    }

    /**
     * Меняет оценку
     * @param grade оценка
     * @param newValue новое значение оценки
     */
    @Override
    public void changeGrade(Grade grade, int newValue) {
        Optional<Grade> newGrade = gradeRepository.findById(grade.getId());
        if (newGrade.isEmpty()){
            throw new GradeNotFoundException();
        }
        newGrade.get().setValue(newValue);
        gradeRepository.save(newGrade.get());
    }
}
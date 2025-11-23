package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.GradeNotFoundException;
import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.rules.GradeRule;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Реализация сервиса для управления оценками.
 * Сервис предоставляет операции для создания, удаления, нахождения и смены оценки
 * взаимодействуя с {@link GradeRepository} для выполнения операций с базой данных.
 */
@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final List<GradeRule> gradeRules;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, List<GradeRule> gradeRules, StudentRepository studentRepository,
                            GroupRepository groupRepository, SubjectRepository subjectRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeRules = gradeRules;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void addGrade(Student student, int value, Subject subject, Teacher teacher, LocalDateTime dateTime) {
        Grade grade = new Grade(value, student, subject, teacher, dateTime);
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }
        gradeRepository.save(grade);
    }

    @Override
    public Grade findGrade(long studentId, Subject subject, int value, LocalDateTime dateTime) {
        return gradeRepository.findByStudentIdAndSubjectIdAndValueAndDate(studentId, subject.getId(), value, dateTime).orElseThrow(GradeNotFoundException::new);
    }

    @Override
    public Grade findById(long id) {
        return gradeRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void deleteGradeFromStudent(long studentId, Subject subject, int value, LocalDateTime dateTime) {
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
        Grade existingGrade = gradeRepository.findById(grade.getId()).orElseThrow(GradeNotFoundException::new);

        existingGrade.setValue(newValue);
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }

        gradeRepository.save(existingGrade);
    }

    @Override
    public List<Grade> getGradesByStudentLoginAndSubject(String login, Long subjectId) {
        studentRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Student with login " + login + " not found"));
        subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id - " + subjectId + " not found"));
        return gradeRepository.findAllByStudentLoginAndSubjectId(login, subjectId);
    }

    @Override
    public List<Grade> getGradesBySubjectAndGroup(Long subjectId, Long groupId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id - " + subjectId + " not found"));
        groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id - " + groupId + " not found"));
        return gradeRepository.findAllBySubjectIdAndGroup(subjectId, groupId);
    }

    @Override
    public List<Grade> getGradesByGroup(Long groupId) {
        groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id - " + groupId + " not found"));
        return gradeRepository.findAllByGroupId(groupId);
    }

    @Override
    public List<Grade> getGradesByStudent(String login) {
        studentRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Student with login " + login + " not found"));
        return gradeRepository.findAllByStudentLogin(login);
    }
}
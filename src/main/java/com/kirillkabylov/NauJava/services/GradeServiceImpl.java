package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.*;
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
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, List<GradeRule> gradeRules,
                            StudentRepository studentRepository, GroupRepository groupRepository,
                            SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeRules = gradeRules;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Grade createGrade(Long studentId, int value, Long subjectId, Long teacherId, LocalDateTime dateTime) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id - " + studentId + " not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id - " + studentId + " not found"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new EntityNotFoundException("Teacher with id - " + studentId + " not found"));
        Grade grade = new Grade(value, student, subject, teacher, dateTime);
        for (GradeRule rule : gradeRules) {
            rule.validate(grade);
        }

        return gradeRepository.save(grade);
    }

    @Override
    public Grade getGrade(long studentId, Subject subject, int value, LocalDateTime dateTime) {
        return gradeRepository
                .findByStudentIdAndSubjectIdAndValueAndDate(studentId, subject.getId(), value, dateTime)
                .orElseThrow(() -> new EntityNotFoundException("Grade with not found"));
    }

    public Optional<Grade> findGrade(long studentId, Subject subject, int value, LocalDateTime dateTime) {
        return gradeRepository.findByStudentIdAndSubjectIdAndValueAndDate(studentId, subject.getId(), value, dateTime);
    }

    @Override
    public Optional<Grade> findById(long id) {
        return gradeRepository.findById(id);
    }

    @Override
    public void deleteGradeFromStudent(long studentId, Subject subject, int value, LocalDateTime dateTime) {
        Optional<Grade> grade = findGrade(studentId, subject, value, dateTime);
        grade.ifPresent(gradeRepository::delete);
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
        Grade existingGrade = gradeRepository
                .findById(grade.getId())
                .orElseThrow(() -> new EntityNotFoundException("Grade with not found"));

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
        return gradeRepository.findAllByStudentGroupId(groupId);
    }

    @Override
    public List<Grade> getGradesByStudent(String login) {
        studentRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Student with login " + login + " not found"));
        return gradeRepository.findAllByStudentLogin(login);
    }

    @Override
    public List<Grade> getGradesByGroupIdAndDateBetween(Long groupId, LocalDateTime start, LocalDateTime end) {
        return gradeRepository.findGradesByGroupAndDate(groupId, start, end);
    }

    @Override
    public double getAverage(Long studentId, Long subjectId) {
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId)
                .stream()
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public double getAverage(String login, Long subjectId) {
        Long studentId = studentRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Student with login - " + login + " not found")).getId();
        return gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId)
                .stream()
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0.0);
    }
}
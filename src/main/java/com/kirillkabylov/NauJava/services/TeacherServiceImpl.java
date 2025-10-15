package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final GradeService gradeService;

    public TeacherServiceImpl(TeacherRepository teacherRepository, GradeService gradeService) {
        this.teacherRepository = teacherRepository;
        this.gradeService = gradeService;
    }

    @Override
    public void createTeacher(Long id, String login, String fullName, String password, String subject) {
        Teacher newTeacher = new Teacher(id, login, fullName, password, subject);
        teacherRepository.create(newTeacher);
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    public void updateLogin(Long id, String newLogin) {
        Teacher teacher = teacherRepository.read(id);
        //???????????????? ???????????????? ???? ????????????????
        teacher.setLogin(newLogin);
        teacherRepository.update(teacher);
    }

    @Override
    public void updateFullName(Long id, String newFullName) {
        Teacher teacher = teacherRepository.read(id);
        //???????????????? ???????????????? ???? ???????????????????? ??????????????
        teacher.setFullName(newFullName);
        teacherRepository.update(teacher);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        Teacher teacher = teacherRepository.read(id);
        //???????????????? ???????????????? ???? ?????????????? ????????????
        teacher.setPassword(newPassword);
        teacherRepository.update(teacher);
    }

    @Override
    public void updateSubject(Long id, String newSubjectName) {
        Teacher teacher = teacherRepository.read(id);
        teacher.setSubject(newSubjectName);
        teacherRepository.update(teacher);
    }

    @Override
    public void addGrade(Long id,Long studentId, String subject, int value, Long teacherId) {
        gradeService.addGrade(id, studentId, subject, value, teacherId);
    }

    @Override
    public void addGrade(Long id, int value, Student student, String subject, Teacher teacher) {
        gradeService.addGrade(id, value, student, subject, teacher);
    }

    @Override
    public void deleteGrade(Long studentId, String subject, int value, LocalDateTime dateTime) {
        gradeService.deleteGrade(studentId, subject, value, dateTime);
    }

    @Override
    public void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime) {
        gradeService.deleteGrade(student, subject, value, dateTime);
    }

    @Override
    public void deleteGrade(Long gradeId) {
        gradeService.deleteGrade(gradeId);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeService.deleteGrade(grade);
    }

    @Override
    public void printAllTeachers(){
        for (Teacher teacher : teacherRepository.findAll()){
            System.out.println(teacher);
        }
    }
}

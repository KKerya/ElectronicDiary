package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final GradeService gradeService;

    public TeacherServiceImpl(TeacherRepository teacherRepository, GradeService gradeService) {
        this.teacherRepository = teacherRepository;
        this.gradeService = gradeService;
    }

    @Override
    public void createTeacher(Long id, String login, String fullName, String password, Subject subject) {
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
        teacher.getSubject().setName(newSubjectName);
        teacherRepository.update(teacher);
    }

    @Override
    public void addGrade(Student student, Subject subject, int value, Teacher teacher) {
        gradeService.addGrade(student, subject, value, teacher);
    }

    @Override
    public void deleteGrade(Student student, Subject subject, int value, Teacher teacher) {
        gradeService.deleteGrade(student, subject, value, teacher);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeService.deleteGrade(grade);
    }

}

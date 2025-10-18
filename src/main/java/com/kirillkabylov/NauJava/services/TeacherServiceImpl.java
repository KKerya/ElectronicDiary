//package com.kirillkabylov.NauJava.services;
//
//import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
//import com.kirillkabylov.NauJava.database.TeacherRepository;
//import com.kirillkabylov.NauJava.domain.Grade;
//import com.kirillkabylov.NauJava.domain.Student;
//import com.kirillkabylov.NauJava.domain.Teacher;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class TeacherServiceImpl implements TeacherService {
//    private final TeacherRepository teacherRepository;
//    private final GradeService gradeService;
//
//    public TeacherServiceImpl(TeacherRepository teacherRepository, GradeService gradeService) {
//        this.teacherRepository = teacherRepository;
//        this.gradeService = gradeService;
//    }
//
//    @Override
//    public void createTeacher(Long id, String login, String fullName, String password, String subject) {
//        Teacher newTeacher = new Teacher(id, login, fullName, password, subject);
//        teacherRepository.create(newTeacher);
//    }
//
//    @Override
//    public Teacher findById(Long id) {
//        Optional<Teacher> teacher = teacherRepository.read(id);
//        if (teacher.isPresent()) {
//            return teacher.get();
//        }
//        throw new UserNotFoundException(id);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        teacherRepository.delete(id);
//    }
//
//    @Override
//    public void updateTeacher(Long id, String field, String newValue) {
//        Optional<Teacher> teacher = teacherRepository.read(id);
//        if (teacher.isEmpty()) {
//            throw new UserNotFoundException(id);
//        }
//
//        switch (field) {
//            case "login" -> teacher.get().setLogin(newValue);
//            case "password" -> teacher.get().setPassword(newValue);
//            case "fullName" -> teacher.get().setFullName(newValue);
//            case "Subject" -> teacher.get().setSubject(newValue);
//            default -> {
//                System.out.println("Неизвестное поле: " + field);
//                return;
//            }
//        }
//
//        teacherRepository.update(teacher.get());
//    }
//
//    @Override
//    public void addGrade(Long id, Long studentId, String subject, int value, Long teacherId) {
//        gradeService.addGrade(id, studentId, subject, value, teacherId);
//    }
//
//    @Override
//    public void addGrade(Long id, int value, Student student, String subject, Teacher teacher) {
//        gradeService.addGrade(id, value, student, subject, teacher);
//    }
//
//    @Override
//    public void deleteGrade(Long studentId, String subject, int value, LocalDateTime dateTime) {
//        gradeService.deleteGrade(studentId, subject, value, dateTime);
//    }
//
//    @Override
//    public void deleteGrade(Student student, String subject, int value, LocalDateTime dateTime) {
//        gradeService.deleteGrade(student, subject, value, dateTime);
//    }
//
//    @Override
//    public void deleteGrade(Long gradeId) {
//        gradeService.deleteGrade(gradeId);
//    }
//
//    @Override
//    public void deleteGrade(Grade grade) {
//        gradeService.deleteGrade(grade);
//    }
//
//    @Override
//    public void printAllTeachers() {
//        for (Teacher teacher : teacherRepository.findAll()) {
//            System.out.println(teacher);
//        }
//    }
//}
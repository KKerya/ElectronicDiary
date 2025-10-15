package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GradeService gradeService;         //наверно так неправильно, и нарушаю SRP. По-хорошему создать отдельный сервис?
    private final LessonRepository lessonRepository;

    public StudentServiceImpl(StudentRepository studentRepository, GradeService gradeService, LessonRepository lessonRepository) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void createStudent(Long id, String login, String fullName, String password, String groupName) {
        Student newStudent = new Student(id, login, fullName, password, groupName);
        studentRepository.create(newStudent);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public void updateLogin(Long id, String newLogin) {
        Student student = studentRepository.read(id);
        student.setLogin(newLogin);
        studentRepository.update(student);
    }

    @Override
    public void updateFullName(Long id, String fullName) {
        Student student = studentRepository.read(id);
        student.setFullName(fullName);
        studentRepository.update(student);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        Student student = studentRepository.read(id);
        student.setPassword(newPassword);
        studentRepository.update(student);
    }

    @Override
    public void updateGroupName(Long id, String newGroupName) {
        Student student = studentRepository.read(id);
        student.setGroupName(newGroupName);
        studentRepository.update(student);
    }

    @Override
    public void printAllStudents() {
        for (Student student : studentRepository.findAll()) {
            System.out.println(student);
        }
    }

    @Override
    public void printGrades(Long studentId, String subject) {
        gradeService.printGradesByStudent(studentId, subject);
    }

    @Override
    public void printAllGrades(Long studentId) {
        gradeService.printAllGradesByStudent(studentId);
    }

    @Override
    public void checkSchedule(Long studentId) {
        Student student = studentRepository.read(studentId);
        if (student == null) {
            System.out.println("Студент не найден");
        }

        String groupName = student.getGroupName();
        List<Lesson> lessons = lessonRepository.findByGroupName(groupName);

        if (lessons.isEmpty()) {
            System.out.println("Расписание отсутствует");
        } else {
            System.out.println("Расписание для группы " + groupName + ":");
            for (Lesson lesson : lessons) {
                System.out.println(
                        "Предмет: " + lesson.getSubject() +
                                ", Преподаватель: " + lesson.getTeacher() +
                                ", Время начала: " + lesson.getStartTime() +
                                ", Кабинет: " + lesson.getRoom()
                );
            }
        }
    }
}
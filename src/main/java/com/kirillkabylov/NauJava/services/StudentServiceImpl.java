package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<Student> findById(Long id) {
        return studentRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public void updateStudent(Long id, String field, String newValue) {
        Optional<Student> student = studentRepository.read(id);
        if (student.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        switch (field) {
            case "login" -> student.get().setLogin(newValue);
            case "password" -> student.get().setPassword(newValue);
            case "fullName" -> student.get().setFullName(newValue);
            case "groupName" -> student.get().setGroupName(newValue);
            default -> {
                System.out.println("Неизвестное поле: " + field);
                return;
            }
        }

        studentRepository.update(student.get());
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
        Optional<Student> student = studentRepository.read(studentId);
        if (student.isEmpty()) {
            throw new UserNotFoundException(studentId);
        }

        String groupName = student.get().getGroupName();
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
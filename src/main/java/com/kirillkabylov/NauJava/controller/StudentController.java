package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.UserEntity;
import com.kirillkabylov.NauJava.dto.StudentDto;
import com.kirillkabylov.NauJava.services.StudentService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    /**
     * Создать студента
     *
     * @param login   логин пользователя
     * @param groupId id группы
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public void createStudent(@RequestParam String login, @RequestParam Long groupId) {
        UserEntity user = userService.getByLogin(login);
        studentService.promoteToStudent(user, groupId);
    }

    /**
     * Получить студентов
     *
     * @param lessonId id занятия
     */
    @GetMapping("/by-lesson")
    @PreAuthorize("hasRole('TEACHER')")
    public List<StudentDto> getStudentsByLesson(@RequestParam Long lessonId) {
        return studentService.getStudentsByLesson(lessonId).stream()
                .map(student -> new StudentDto(student.getId(), student.getFullName(), student.getGroup().getName()))
                .toList();
    }

    /**
     * Получить студентов
     *
     * @param groupId id группы
     * @return
     */
    @GetMapping("/by-group")
    @PreAuthorize("hasRole('TEACHER')")
    public List<StudentDto> getStudentsByGroup(@RequestParam Long groupId) {
        return studentService.getStudentsByGroupId(groupId).stream()
                .map(student -> new StudentDto(student.getId(), student.getFullName(), student.getGroup().getName()))
                .toList();
    }
}

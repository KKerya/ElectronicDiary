package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.StudentDto;
import com.kirillkabylov.NauJava.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private final LessonService lessonService;

    @Autowired
    public StudentController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public List<StudentDto> getStudentsByLesson(@RequestParam Long lessonId) {
        return lessonService.getStudentsForLesson(lessonId).stream()
                .map(student -> new StudentDto(student.getId(), student.getFullName(), student.getGroup().getName()))
                .toList();
    }
}

package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("custom/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Teacher> getTeacher(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String subject) {
        if (name != null && subject != null) {
            return teacherRepository.findByFullNameAndSubject(name, subject);
        }

        if (name != null) {
            return teacherRepository.findByFullName(name);
        }

        if (subject != null) {
            return teacherRepository.findBySubject(subject);
        }

        return (List<Teacher>) teacherRepository.findAll();
    }
}

package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("custom/lessons")
public class LessonController {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @GetMapping
    public List<Lesson> getLessonByTeacherName(@RequestParam String name) {
        return lessonRepository.findLessonByTeacherName(name);
    }
}

package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.CreateLessonRequest;
import com.kirillkabylov.NauJava.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/lesson")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLesson(@RequestBody CreateLessonRequest request) {
        lessonService.createLesson(request.groupId(), request.subjectId(), request.teacherId(), request.startTime(), request.wholeYear());
        return ResponseEntity.ok("OK");
    }
}

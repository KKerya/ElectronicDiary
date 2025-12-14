package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.dto.CreateLessonRequest;
import com.kirillkabylov.NauJava.dto.LessonDto;
import com.kirillkabylov.NauJava.services.LessonService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final UserService userService;

    @Autowired
    public LessonController(LessonService lessonService, UserService userService) {
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLesson(@RequestBody CreateLessonRequest request) {
        lessonService.createLesson(request.groupId(), request.subjectId(), request.teacherId(), request.startTime(), request.wholeYear());
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/api/lessons")
    @PreAuthorize("hasRole('TEACHER')")
    public List<LessonDto> getLessons(
            @RequestParam Long subjectId,
            @RequestParam Long groupId,
            @AuthenticationPrincipal UserDetails user) {
        Long teacherId = userService.getByLogin(user.getUsername()).getId();
        return lessonService.getLessonForTeacher(teacherId, subjectId, groupId)
                .stream()
                .map(lesson -> new LessonDto(
                        lesson.getId(),
                        lesson.getStartTime(),
                        lesson.getSubject().getId(),
                        lesson.getGroup().getId(),
                        lesson.getDurationMinutes()
                ))
                .toList();
    }
}

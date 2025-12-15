package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.dto.HomeworkDto;
import com.kirillkabylov.NauJava.services.HomeworkService;
import com.kirillkabylov.NauJava.services.LessonService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/homework")
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final UserService userService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService, UserService userService) {
        this.homeworkService = homeworkService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public void createHomework(@RequestParam Long lessonId,
                                 @RequestParam String description,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        homeworkService.createHomework(description, lessonId, deadline);
    }

    @GetMapping("")
    @ResponseBody
    public List<HomeworkDto> getHomeworkForSubject(@AuthenticationPrincipal UserDetails user,
                                                   @RequestParam Long subjectId) {
        Long studentId = userService.getByLogin(user.getUsername()).getId();
        return homeworkService.getHomeworkForStudentBySubject(studentId, subjectId).stream()
                .map(homework -> new HomeworkDto(
                        homework.getId(),
                        homework.getDescription(),
                        homework.getCreatedAt(),
                        homework.getDeadline()
                ))
                .toList();
    }
}

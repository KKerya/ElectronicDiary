package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.TeacherSubjectDTO;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public void createSubject(@RequestBody Map<String, String> request) {
        subjectService.createSubject(request.get("name").trim());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set")
    public void setSubjectTeacher(@RequestBody TeacherSubjectDTO request) {
        subjectService.setSubjectTeacher(request.subjectId(), request.teacherId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove")
    public void removeSubjectFromTeacher(@RequestBody TeacherSubjectDTO request) {
        subjectService.removeSubjectFromTeacher(request.subjectId(), request.teacherId());
    }
}

package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentReportController {
    private final SubjectService subjectService;

    @Autowired
    public StudentReportController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/student/report")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentReportPage(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student/studentReport";
    }
}
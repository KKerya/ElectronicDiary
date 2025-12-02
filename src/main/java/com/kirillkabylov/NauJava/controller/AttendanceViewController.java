package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AttendanceViewController {
    private final SubjectService subjectService;

    @Autowired
    public AttendanceViewController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/attendance")
    @PreAuthorize("hasRole('STUDENT')")
    public String attendancePage(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "attendance";
    }
}

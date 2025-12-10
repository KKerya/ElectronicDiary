package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final SubjectService subjectService;

    @Autowired
    public AdminPageController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Отдать страницу назначения учителем
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create/teacher")
    public String getMakeTeacherPage(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);

        return "makeTeacher";
    }
}
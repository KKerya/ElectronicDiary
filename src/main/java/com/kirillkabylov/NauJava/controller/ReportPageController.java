package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.services.GroupService;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportPageController {
    private final SubjectService subjectService;
    private final GroupService groupService;

    @Autowired
    public ReportPageController(SubjectService subjectService, GroupService groupService) {
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentReportPage(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student/studentReport";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String teacherReportPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("subjects", subjectService.getSubjectsByTeacherLogin(userDetails.getUsername()));
        model.addAttribute("groups", groupService.getAllGroups());
        return "teacher/teacherReport";
    }
}
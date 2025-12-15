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
@RequestMapping("/homework")
public class HomeworkPageController {
    private final GroupService groupService;
    private final SubjectService subjectService;

    @Autowired
    public HomeworkPageController(GroupService groupService, SubjectService subjectService) {
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public String createHomeworkPage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("subjects", subjectService.getSubjectsByTeacherLogin(user.getUsername()));
        model.addAttribute("groups", groupService.getAllGroups());
        return "teacher/homeworkCreate";
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('STUDENT')")
    public String viewHomeworkPage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "student/homeworkView";
    }
}

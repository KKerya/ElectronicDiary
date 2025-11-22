package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.services.GradeService;
import com.kirillkabylov.NauJava.services.GroupService;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GradeViewController {
    private final SubjectService subjectService;
    private final GroupService groupService;

    @Autowired
    public GradeViewController(SubjectService subjectService, GroupService groupService) {
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    @GetMapping("/grades")
    public String gradesPage(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        boolean isTeacher = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        model.addAttribute("isTeacher", isTeacher);

        if (isTeacher) {
            model.addAttribute("groups", groupService.getAllGroups());
        }

        return "grades";
    }
}

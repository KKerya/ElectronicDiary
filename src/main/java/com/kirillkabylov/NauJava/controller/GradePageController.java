package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.config.GradeProperties;
import com.kirillkabylov.NauJava.services.GroupService;
import com.kirillkabylov.NauJava.services.StudentService;
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
@RequestMapping("grade")
public class GradePageController {
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final GradeProperties gradeProperties;

    @Autowired
    public GradePageController(SubjectService subjectService, GroupService groupService, StudentService studentService, GradeProperties gradeProperties) {
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.studentService = studentService;
        this.gradeProperties = gradeProperties;
    }

    @GetMapping("/page")
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

    @GetMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public String createGradePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("minScore", gradeProperties.getMinScore());
        model.addAttribute("maxScore", gradeProperties.getMaxScore());
        model.addAttribute("subjects", subjectService.getSubjectsByTeacherLogin(user.getUsername()));
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("students", studentService.getAllStudents());

        return "teacher/createGrade";
    }
}

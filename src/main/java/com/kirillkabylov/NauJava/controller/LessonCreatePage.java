package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.services.GroupService;
import com.kirillkabylov.NauJava.services.SubjectService;
import com.kirillkabylov.NauJava.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("lesson")
@PreAuthorize("hasRole('ADMIN')")
public class LessonCreatePage {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Autowired
    public LessonCreatePage(GroupService groupService, SubjectService subjectService, TeacherService teacherService) {
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping("/create")
    public String showCreateLessonForm(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("teachers", teacherService.getAllTeacher());

        return "createLesson";
    }
}

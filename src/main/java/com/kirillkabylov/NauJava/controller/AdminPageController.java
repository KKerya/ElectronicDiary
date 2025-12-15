package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.services.GroupService;
import com.kirillkabylov.NauJava.services.SubjectService;
import com.kirillkabylov.NauJava.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Autowired
    public AdminPageController(GroupService groupService, SubjectService subjectService, TeacherService teacherService) {
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }


    /**
     * Отдать страницу назначения учителем
     */
    @GetMapping("/create/teacher")
    public String getMakeTeacherPage(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);

        return "admin/makeTeacher";
    }

    @GetMapping("/create/subject")
    public String showCreateSubjectPage(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Teacher> teachers = teacherService.getAllTeacher();

        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);

        return "admin/manageSubject";
    }

    @GetMapping("/change/password")
    public String changePasswordPage() {
        return "admin/changePassword";
    }

    @GetMapping("lesson/create")
    public String showCreateLessonForm(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("teachers", teacherService.getAllTeacher());

        return "admin/createLesson";
    }
}
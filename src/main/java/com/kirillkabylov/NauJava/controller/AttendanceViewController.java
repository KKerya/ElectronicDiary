package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.dto.LessonDto;
import com.kirillkabylov.NauJava.dto.SubjectDto;
import com.kirillkabylov.NauJava.services.LessonService;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceViewController {
    private final SubjectService subjectService;
    private final LessonService lessonService;

    @Autowired
    public AttendanceViewController(SubjectService subjectService, LessonService lessonService) {
        this.subjectService = subjectService;
        this.lessonService = lessonService;
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('STUDENT')")
    public String attendancePage(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "attendance";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public String attendanceCreatePage(@AuthenticationPrincipal UserDetails user, Model model) {
        List<SubjectDto> subjects = subjectService.getSubjectsByTeacherLogin(user.getUsername())
                                    .stream()
                                    .map(s -> new SubjectDto(s.getId(), s.getName())).toList();
        List<LessonDto> lessons = lessonService.getLessonsByTeacherLogin(user.getUsername())
                                    .stream()
                                    .map(l -> new LessonDto(l.getId(), l.getStartTime(), l.getSubject().getId(), l.getDurationMinutes())).toList();

        model.addAttribute("subjects", subjects);
        model.addAttribute("lessons", lessons);

        return "attendanceCreate";
    }
}

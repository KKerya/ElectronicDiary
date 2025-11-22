package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.Exceptions.GradeNotFoundException;
import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.dto.GradeDto;
import com.kirillkabylov.NauJava.services.GradeService;
import com.kirillkabylov.NauJava.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GradeController {
    private final GradeRepository gradeRepository;
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeRepository gradeRepository, GradeService gradeService) {
        this.gradeRepository = gradeRepository;
        this.gradeService = gradeService;
    }

    @GetMapping("/student")
    public List<Grade> getGradesByStudentId(@RequestParam Long studentId){
        return gradeRepository.findAllByStudentId(studentId);
    }

    @GetMapping("/filter")
    public Grade getGradeByStudentIdAndSubjectAndValueAndDate(@RequestParam Long studentId,
                                                                    @RequestParam Long subjectId,
                                                                    @RequestParam int value,
                                                                    @RequestParam LocalDateTime date)
    {
       return gradeRepository.findByStudentIdAndSubjectIdAndValueAndDate(studentId, subjectId, value, date).orElseThrow(GradeNotFoundException::new);
    }

    @GetMapping("/grades")
    public List<GradeDto> getGrades(
            @RequestParam Long subjectId,
            @RequestParam(required = false) Long groupId,
            @AuthenticationPrincipal UserDetails user) {

        boolean isTeacher = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        List<Grade> grades;

        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER")) && groupId != null) {
            grades = gradeService.getGradesBySubjectAndGroup(subjectId, groupId);
        } else {
            grades = gradeService.getGradesByStudentLoginAndSubject(user.getUsername(), subjectId);
        }

        return grades.stream()
                .map(g -> new GradeDto(
                        g.getStudent().getFullName(),
                        g.getTeacher().getFullName(),
                        g.getValue(),
                        g.getDate()
                ))
                .toList();
    }
}

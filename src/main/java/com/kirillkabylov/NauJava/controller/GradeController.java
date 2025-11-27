package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.dto.GradeCreateRequest;
import com.kirillkabylov.NauJava.dto.GradeDto;
import com.kirillkabylov.NauJava.dto.StudentGradesDto;
import com.kirillkabylov.NauJava.services.GradeService;
import com.kirillkabylov.NauJava.services.StudentService;
import com.kirillkabylov.NauJava.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grade")
public class GradeController {
    private final GradeService gradeService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public GradeController(GradeService gradeService, StudentService studentService, TeacherService teacherService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/group-month")
    public List<StudentGradesDto> getGradesByGroupAndMonth(
            @RequestParam Long groupId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);

        List<Grade> grades = gradeService.getGradesByGroupIdAndDateBetween(groupId, start, end);

        List<StudentGradesDto> result = new ArrayList<>();
        Map<Student, List<Grade>> grouped = grades.stream()
                .collect(Collectors.groupingBy(Grade::getStudent));

        for (Map.Entry<Student, List<Grade>> entry : grouped.entrySet()) {
            Map<Integer, Integer> dayGrades = new HashMap<>();
            for (Grade g : entry.getValue()) {
                dayGrades.put(g.getDate().getDayOfMonth(), g.getValue());
            }
            result.add(new StudentGradesDto(entry.getKey().getId(),
                    entry.getKey().getFullName(),
                    dayGrades));
        }

        return result;
    }

    @GetMapping
    public List<GradeDto> getGradesForStudentOrTeacher(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long groupId,
            @AuthenticationPrincipal UserDetails user) {

        boolean isTeacher = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        List<Grade> grades;

        if (isTeacher && groupId != null) {
            if (subjectId != null) {
                grades = gradeService.getGradesBySubjectAndGroup(subjectId, groupId);
            } else {
                grades = gradeService.getGradesByGroup(groupId);
            }
        } else {
            if (subjectId != null) {
                grades = gradeService.getGradesByStudentLoginAndSubject(user.getUsername(), subjectId);
            } else {
                grades = gradeService.getGradesByStudent(user.getUsername());
            }
        }

        return grades.stream()
                .map(g -> new GradeDto(
                        g.getStudent().getFullName(),
                        g.getTeacher().getFullName(),
                        g.getValue(),
                        g.getDate().toLocalDate().atTime(LocalTime.now())
                ))
                .toList();
    }

    @PostMapping("/create")
    public Grade createGrade(@RequestBody GradeCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        if (login == null) {
            throw new RuntimeException("Not authorized");
        }
        Teacher teacher = teacherService.getByLogin(login);

        return gradeService.createGrade(
                request.studentId(),
                request.value(),
                request.subjectId(),
                teacher.getId(),
                request.date().atTime(0, 0)
        );
    }

}

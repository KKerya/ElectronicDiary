package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.config.GradeProperties;
import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.dto.GradeCreateRequest;
import com.kirillkabylov.NauJava.dto.GradeDistributionDto;
import com.kirillkabylov.NauJava.dto.GradeDto;
import com.kirillkabylov.NauJava.dto.StudentGradesDto;
import com.kirillkabylov.NauJava.services.GradeService;
import com.kirillkabylov.NauJava.services.TeacherService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grade")
@PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT') or hasRole('ADMIN')")
public class GradeController {
    private final GradeService gradeService;
    private final TeacherService teacherService;
    private final UserService userService;
    private final GradeProperties gradeProperties;

    @Autowired
    public GradeController(GradeService gradeService, TeacherService teacherService, UserService userService, GradeProperties gradeProperties) {
        this.gradeService = gradeService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.gradeProperties = gradeProperties;
    }

    /**
     * Получить оценки группы за определенную дату
     * @param groupId id группы
     * @param month год и месяц
     */
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/group-month")
    public List<StudentGradesDto> getGradesByGroupAndMonth(
            @RequestParam Long groupId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

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

    /**
     * Получить оценки
     * @param subjectId id предмета
     * @param groupId id группы
     * @param user пользователь
     */
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
                        g.getDate()
                ))
                .toList();
    }

    /**
     * Создать оценку
     * @param request
     */
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public Grade createGrade(@AuthenticationPrincipal UserDetails user, @RequestBody GradeCreateRequest request) {
        Teacher teacher = teacherService.getByLogin(user.getUsername());
        return gradeService.createGrade(
                request.studentId(),
                request.value(),
                request.subjectId(),
                teacher.getId(),
                request.date()
        );
    }

    /**
     * Среднее арифметическое оценок пользователя
     * @param user пользователь
     * @param subjectId id предмета
     * @return
     */
    @GetMapping("student/average")
    public double getAverageForStudent(@AuthenticationPrincipal UserDetails user, @RequestParam Long subjectId) {
        Long userId = userService.getByLogin(user.getUsername()).getId();
        return gradeService.getAverage(userId, subjectId);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("teacher/average")
    public double getAverageForTeacher(@AuthenticationPrincipal UserDetails user, @RequestParam Long subjectId, @RequestParam Long groupId) {
        Long userId = userService.getByLogin(user.getUsername()).getId();
        return gradeService.getAverage(userId, subjectId, groupId);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("subject/count")
    public long getCountGrades(@RequestParam Long subjectId, @RequestParam Long groupId) {
        return gradeService.getGradesCount(subjectId, groupId);
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("subject/distribution")
    public List<GradeDistributionDto> getDistribution(@RequestParam Long subjectId, @RequestParam Long groupId) {
        Map<Integer, Long> counts = gradeService.getGradeDistribution(subjectId, groupId);

        List<GradeDistributionDto> distribution = new ArrayList<>();
        for (int i = gradeProperties.getMinScore(); i <= gradeProperties.getMaxScore(); i++) {
            distribution.add(new GradeDistributionDto(i, counts.getOrDefault(i, 0L)));
        }

        return distribution;
    }
}
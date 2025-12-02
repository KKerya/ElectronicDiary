package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Attendance;
import com.kirillkabylov.NauJava.dto.AttendanceDto;
import com.kirillkabylov.NauJava.dto.StudentDto;
import com.kirillkabylov.NauJava.services.AttendanceService;
import com.kirillkabylov.NauJava.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final StudentService studentService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
    }

    @GetMapping("/my-attendance/")
    @PreAuthorize("hasRole('STUDENT')")
    public List<AttendanceDto> getAllAttendance(@AuthenticationPrincipal UserDetails user) {
        Long studentId = studentService.getByLogin(user.getUsername()).getId();
        List<Attendance> attendances = attendanceService.getAllStudentAttendance(studentId);

        return attendances.stream()
                .map(a -> new AttendanceDto(
                        a.getLesson().getStartTime().toLocalDate(),
                        new StudentDto(a.getStudent().getId(), a.getStudent().getFullName(), a.getStudent().getGroup().getName()),
                        a.getStatus().getDisplayName()
                ))
                .toList();
    }


    @GetMapping("/my-attendance/subject/{subjectId}")
    @PreAuthorize("hasRole('STUDENT')")
    public List<AttendanceDto> getAllAttendance(@AuthenticationPrincipal UserDetails user, @PathVariable Long subjectId) {
        Long studentId = studentService.getByLogin(user.getUsername()).getId();
        List<Attendance> attendances = attendanceService.getStudentAttendanceBySubject(studentId, subjectId);

        return attendances.stream()
                .map(a -> new AttendanceDto(
                        a.getLesson().getStartTime().toLocalDate(),
                        new StudentDto(a.getStudent().getId(), a.getStudent().getFullName(), a.getStudent().getGroup().getName()),
                        a.getStatus().getDisplayName()
                ))
                .toList();
    }

    @GetMapping("/for-admin/my-attendance/student/{studentId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public List<AttendanceDto> getAllAttendance(@PathVariable Long studentId) {
        List<Attendance> attendances = attendanceService.getAllStudentAttendance(studentId);

        return attendances.stream()
                .map(a -> new AttendanceDto(
                        a.getLesson().getStartTime().toLocalDate(),
                        new StudentDto(a.getStudent().getId(), a.getStudent().getFullName(), a.getStudent().getGroup().getName()),
                        a.getStatus().getDisplayName()
                ))
                .toList();
    }
}

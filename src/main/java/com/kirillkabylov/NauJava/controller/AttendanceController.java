package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Attendance;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.dto.AttendanceCreateRequest;
import com.kirillkabylov.NauJava.dto.AttendanceDto;
import com.kirillkabylov.NauJava.dto.StudentAttendanceDto;
import com.kirillkabylov.NauJava.dto.StudentDto;
import com.kirillkabylov.NauJava.enums.AttendanceStatus;
import com.kirillkabylov.NauJava.services.AttendanceService;
import com.kirillkabylov.NauJava.services.LessonService;
import com.kirillkabylov.NauJava.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final LessonService lessonService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, StudentService studentService, LessonService lessonService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.lessonService = lessonService;
    }

    /**
     * Получить все посещения студента
     */
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


    /**
     * Получить все посещения студента по конкретному предмету
     * @param subjectId id предмета
     */
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

    /**
     * Получить все посещения по студенту
     * @param studentId id студента
     */
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

    /**
     * Создать посещение
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public AttendanceDto createAttendance(@RequestBody AttendanceCreateRequest request) {
        Attendance attendance = attendanceService.createAttendance(
                request.lessonId(),
                request.studentId(),
                AttendanceStatus.valueOf(request.status())
        );

        return new AttendanceDto(
                attendance.getLesson().getStartTime().toLocalDate(),
                new StudentDto(
                        attendance.getStudent().getId(),
                        attendance.getStudent().getFullName(),
                        attendance.getStudent().getGroup().getName()
                ),
                attendance.getStatus().getDisplayName()
        );
    }

    /**
     * удалить посещение
     */
    @DeleteMapping("delete/{attendanceId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public void deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }

    /**
     * Получить список посещении студентов по занятию
     */
    @GetMapping("/students/{lessonId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public List<StudentAttendanceDto> getStudentsByLesson(@PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        return lesson.getGroup().getStudents().stream()
                .map(student -> {
                    Attendance attendance = lesson.getAttendances().stream()
                            .filter(a -> a.getStudent().getId().equals(student.getId()))
                            .findFirst()
                            .orElse(null);

                    String status = attendance != null ? attendance.getStatus().getDisplayName() : null;

                    return new StudentAttendanceDto(student.getId(), student.getFullName(), status);
                }).toList();
    }
}

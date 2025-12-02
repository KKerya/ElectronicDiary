package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Attendance;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.enums.AttendanceStatus;

import java.util.List;

public interface AttendanceService {
    Attendance createAttendance(Lesson lesson, Student student, AttendanceStatus status);

    List<Attendance> getStudentAttendanceBySubject(Long studentId, Long subjectId);

    List<Attendance> getAllStudentAttendance(Long studentId);
}

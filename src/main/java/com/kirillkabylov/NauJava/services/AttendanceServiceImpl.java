package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.AttendanceRepository;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Attendance;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.enums.AttendanceStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(LessonRepository lessonRepository, StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Attendance createAttendance(Lesson lesson, Student student, AttendanceStatus status) {
        Attendance attendance = attendanceRepository.save(new Attendance(lesson, student, status));
        lesson.getAttendances().add(attendance);
        return attendance;
    }

    @Override
    public Attendance createAttendance(Long lessonId, Long studentId, AttendanceStatus status) {
        Attendance attendance = attendanceRepository
                .findByLessonIdAndStudentId(lessonId, studentId)
                .orElseGet(Attendance::new);

        if (attendance.getId() == null) {
            Lesson lesson = lessonRepository.getReferenceById(lessonId);
            Student student = studentRepository.getReferenceById(studentId);
            attendance = new Attendance(lesson, student, status);
            lesson.getAttendances().add(attendance);
        } else {
            updateAttendanceStatus(attendance, status);
        }

        return attendanceRepository.save(attendance);
    }

    public void updateAttendanceStatus(Attendance attendance, AttendanceStatus status) {
        attendance.setStatus(status);
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public List<Attendance> getStudentAttendanceBySubject(Long studentId, Long subjectId) {
        Group group = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id - " + studentId + "not found")).getGroup();

        List<Lesson> lessons = lessonRepository.findByGroupIdAndSubjectId(group.getId(), subjectId);
        List<Attendance> attendances = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lesson.getAttendances().stream()
                    .filter(a -> a.getStudent().getId().equals(studentId))
                    .findFirst()
                    .ifPresent(attendances::add);
        }

        return attendances;
    }

    @Override
    public List<Attendance> getAllStudentAttendance(Long studentId) {
        Group group = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id - " + studentId + "not found")).getGroup();

        List<Lesson> lessons = lessonRepository.findAll();
        List<Attendance> attendances = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lesson.getAttendances().stream()
                    .filter(a -> a.getStudent().getId().equals(studentId))
                    .findFirst()
                    .ifPresent(attendances::add);
        }

        return attendances;
    }
}
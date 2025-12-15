package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Attendance;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.enums.AttendanceStatus;

import java.util.List;

/**
 * Сервис для управления посещаемости
 */
public interface AttendanceService {
    /**
     * Создать посещение
     */
    Attendance createAttendance(Lesson lesson, Student student, AttendanceStatus status);

    /**
     * Создать посещение
     */
    Attendance createAttendance(Long lessonId, Long studentId, AttendanceStatus status);

    /**
     * Изменить статус посещения
     */
    void updateAttendanceStatus(Attendance attendance, AttendanceStatus status);

    /**
     * Удалить посещение
     */
    void deleteAttendance(Long id);

    /**
     * Получить все посещения студента по предмету
     *
     * @param studentId id студента
     * @param subjectId id предмета
     */
    List<Attendance> getStudentAttendanceBySubject(Long studentId, Long subjectId);

    /**
     * Получить все посещения студента
     *
     * @param studentId id студента
     */
    List<Attendance> getAllStudentAttendance(Long studentId);
}

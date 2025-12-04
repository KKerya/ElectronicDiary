package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByLessonIdAndStudentId(Long lessonId, Long studentId);
}

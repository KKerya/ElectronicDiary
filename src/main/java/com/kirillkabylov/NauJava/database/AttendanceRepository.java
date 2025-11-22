package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Attendance;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
}

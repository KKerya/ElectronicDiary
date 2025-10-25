package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}

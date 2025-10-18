package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StudentRepository extends MemoryRepository<Student> {
    @Autowired
    public StudentRepository(List<Student> studentContainer) {
        super(studentContainer);
    }
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


public class StudentRepository extends MemoryRepository<Student>{
    @Autowired
    public StudentRepository(List<Student> studentContainer){
        super(studentContainer);
    }
}

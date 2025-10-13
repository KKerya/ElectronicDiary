package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeacherRepository extends MemoryRepository<Teacher> {
    @Autowired
    public TeacherRepository(List<Teacher> teacherContainer) {
        super(teacherContainer);
    }
}

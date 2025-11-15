package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Student;

/**
 * Обновление класса у студента
 */
public class UpdateGroupNameCommand implements UserUpdateCommand<Student> {
    @Override
    public void execute(Student student, Object newValue) {
        student.setGroup((Group) newValue);
    }
}
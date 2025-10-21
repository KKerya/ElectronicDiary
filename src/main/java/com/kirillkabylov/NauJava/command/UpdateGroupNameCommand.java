package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.Student;

/**
 * Обновление класса у студента
 */
public class UpdateGroupNameCommand implements UserUpdateCommand<Student> {
    @Override
    public void execute(Student student, String newValue) {
        student.setGroupName(newValue);
    }
}
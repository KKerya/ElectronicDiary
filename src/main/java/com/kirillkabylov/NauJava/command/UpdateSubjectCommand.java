package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.Teacher;

/**
 * Команда для обновления предмета у чителя
 */
public class UpdateSubjectCommand implements UserUpdateCommand<Teacher> {
    @Override
    public void execute(Teacher user, Object newValue) {
        user.setSubject((String)newValue);
    }
}

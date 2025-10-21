package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.User;

/**
 * Обновление пароль у пользователя
 * @param <T> тип пользователя
 */
public class UpdatePasswordCommand<T extends User> implements UserUpdateCommand<T> {
    @Override
    public void execute(T user, String newValue) {
        user.setPassword(newValue);
    }
}
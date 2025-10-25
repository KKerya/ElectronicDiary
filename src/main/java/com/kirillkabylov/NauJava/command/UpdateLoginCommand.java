package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.User;

/**
 * Обновление логина у пользователя
 * @param <T> тип пользователя
 */
public class UpdateLoginCommand<T extends User> implements UserUpdateCommand<T> {
    @Override
    public void execute(T user, Object newValue) {
        user.setLogin((String) newValue);
    }
}
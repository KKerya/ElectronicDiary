package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.User;

/**
 * Обновление полного имени у пользователя
 * @param <T> тип пользователя
 */
public class UpdateFullNameCommand<T extends User> implements UserUpdateCommand<T> {
    @Override
    public void execute(T user, String newValue) {
        user.setFullName(newValue);
    }
}

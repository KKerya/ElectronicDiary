package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.UserEntity;

/**
 * Обновление пароль у пользователя
 * @param <T> тип пользователя
 */
public class UpdatePasswordCommand<T extends UserEntity> implements UserUpdateCommand<T> {
    @Override
    public void execute(T user, Object newValue) {
        user.setPassword((String) newValue);
    }
}
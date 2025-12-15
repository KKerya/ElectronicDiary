package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.UserEntity;

/**
 * Обновление полного имени у пользователя
 *
 * @param <T> тип пользователя
 */
public class UpdateFullNameCommand<T extends UserEntity> implements UserUpdateCommand<T> {
    @Override
    public void execute(T user, Object newValue) {
        user.setFullName((String) newValue);
    }
}

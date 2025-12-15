package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.UserEntity;

public interface UserUpdateCommand<Type1 extends UserEntity> {
    /*
     Думал сделать UserUpdateCommand<Type1 extends User, Type2>
     void execute(Type1 user, Type2 newValue);
     Не знаю стоит ли
     */

    /**
     * Обновляем пользователя
     *
     * @param user     пользователь
     * @param newValue новое значение
     */
    void execute(Type1 user, Object newValue);
}
package com.kirillkabylov.NauJava.command;

public interface UserUpdateCommand<T> {
    /**
     * Обновляем пользователя
     * @param user пользователь
     * @param newValue новое значение
     */
    void execute(T user, String newValue);
}
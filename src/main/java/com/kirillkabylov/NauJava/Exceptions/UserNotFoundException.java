package com.kirillkabylov.NauJava.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Пользователь с id: " + userId + " не найден");
    }
    public UserNotFoundException(String string) {
        super("Пользователь " + string + " не найден");
    }

}

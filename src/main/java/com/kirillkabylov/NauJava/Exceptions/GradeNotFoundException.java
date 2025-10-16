package com.kirillkabylov.NauJava.Exceptions;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException() {
        super("Оценка не найдена");
    }
}
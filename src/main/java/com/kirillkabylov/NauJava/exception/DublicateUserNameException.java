package com.kirillkabylov.NauJava.exception;

public class DublicateUserNameException extends Exception{
    public DublicateUserNameException(String message){
        super(message);
    }
}

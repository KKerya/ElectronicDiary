package com.kirillkabylov.NauJava.Exceptions;

public class ApiError
{
    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static ApiError create(Throwable e){
        return new ApiError(e.getMessage());
    }

    public static ApiError create(String message){
        return new ApiError(message);
    }
}
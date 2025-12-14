package com.kirillkabylov.NauJava.dto;

public record ChangePasswordDto(
        String login,
        String newPassword
) {}
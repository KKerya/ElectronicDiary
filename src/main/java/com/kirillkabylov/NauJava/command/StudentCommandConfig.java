package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Конфигурационный класс Spring для регистрации команд обновления данных студента.
 * Каждая команда реализует интерфейс {@link UserUpdateCommand} и отвечает за изменение
 * конкретного поля объекта {@link Student}, такого как логин, пароль, полное имя или класс
 */
@Configuration
public class StudentCommandConfig {
    @Bean
    public Map<String, UserUpdateCommand<Student>> studentUpdateCommands() {
        return Map.of(
                "login", new UpdateLoginCommand<Student>(),
                "password", new UpdatePasswordCommand<Student>(),
                "fullName", new UpdateFullNameCommand<Student>(),
                "groupName", new UpdateGroupNameCommand()
        );
    }
}
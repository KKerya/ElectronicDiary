package com.kirillkabylov.NauJava.command;

import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Конфигурационный класс Spring для регистрации команд обновления данных преподавателя.
 * Каждая команда реализует интерфейс {@link UserUpdateCommand} и отвечает за изменение
 * конкретного поля объекта {@link Teacher}, такого как логин, пароль, полное имя или предмет
 */
@Configuration
public class TeacherCommandConfig {
    @Bean
    public Map<String, UserUpdateCommand<Teacher>> teacherUpdateCommands() {
        return Map.of(
                "login", new UpdateLoginCommand<Teacher>(),
                "password", new UpdatePasswordCommand<Teacher>(),
                "fullName", new UpdateFullNameCommand<Teacher>(),
                "subject", new UpdateSubjectCommand()
        );
    }
}
package com.kirillkabylov.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Scanner;

@Configuration
public class CommandConfig {
    private CommandProcessor commandProcessor;

    @Autowired
    public CommandConfig(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Bean
    public CommandLineRunner commandScanner() {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in)) {
                List<String> commands = List.of(
                        "student create [id] [логин] [полное имя] [пароль] [класс]",
                        "teacher create [id] [логин] [полное имя] [пароль] [предмет]",
                        "admin create [id] [логин] [полное имя] [пароль]",
                        "teacher/student delete [id пользователя]",
                        "teacher/student find [id пользователя]",
                        "teacher/student updateLogin [id пользователя] [новый логи]",
                        "teacher/student updateFullName [id пользователя] [новое имя] [новая фамилия]",
                        "teacher/student updatePassword [id пользователя] [новый пароль]",
                        "student updateGroupName [id студента] [новый класс]",
                        "student printGrades [id студента] [предмет]",
                        "student printAllGrades [id студента]",
                        "student checkSchedule [id студента]",
                        "teacher/student printAll",
                        "teacher updateSubject [id учителя] [новый предмет]",
                        "teacher addGrade [id оценки] [id студента] [предмет] [оценка] [id учителя]",
                        "teacher deleteGrade [id учителя] [предмет] [оценка] [дата]",
                        "admin addLesson [id] [класс] [предмет] [учитель] [время начало] [кабинет]"
                );
                System.out.println("Набор команд:");
                for (String command : commands) {
                    System.out.println(command);
                }

                System.out.println("Напишите - 'exit' для выхода.");
                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Выход из программы...");
                        break;
                    }
                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}

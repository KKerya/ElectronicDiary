package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.services.AdminService;
import com.kirillkabylov.NauJava.services.StudentService;
import com.kirillkabylov.NauJava.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class CommandProcessor {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdminService adminService;

    @Autowired
    public CommandProcessor(StudentService studentService, TeacherService teacherService, AdminService adminService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        switch (cmd[0]) {
            case "student" -> processStudentCommand(cmd);
            case "teacher" -> processTeacherCommand(cmd);
            case "admin" -> processAdminCommand(cmd);
            default -> System.out.println("Выберите корректный тип");
        }
    }

    private void processStudentCommand(String[] cmd) {
        if (cmd.length <= 1) {
            System.out.println("Некорректная команда");
            return;
        }
        switch (cmd[1]) {
            case "create" -> {
                studentService.createStudent(Long.valueOf(cmd[2]), cmd[3], cmd[4] + " " + cmd[5], cmd[6], cmd[7]);
                System.out.println("Студент успешно создан...");
            }
            case "delete" -> {
                studentService.deleteById(Long.valueOf(cmd[2]));
                System.out.println("Студент успешно удален...");
            }
            case "find" -> {
                Student student = studentService.findById(Long.valueOf(cmd[2]));
                System.out.println(student);
            }
            case "updateLogin" -> {
                studentService.updateLogin(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Логин успешно обновлен...");
            }
            case "updateFullName" -> {
                studentService.updateFullName(Long.valueOf(cmd[2]), cmd[3] + cmd[4]);
                System.out.println("Имя и фамилие успешно обновлено...");
            }
            case "updateGroupName" -> {
                studentService.updateGroupName(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Класс успешно обновлен...");
            }
            case "updatePassword" -> {
                studentService.updatePassword(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Пароль успешно обновлен...");
            }
            case "printAll" -> {
                studentService.printAllStudents();
            }
            case "printGrades" -> {
                studentService.printGrades(Long.valueOf(cmd[2]), cmd[3]);
            }
            case "printAllGrades" -> {
                studentService.printAllGrades(Long.valueOf(cmd[2]));
            }
            case "checkSchedule" -> {
                studentService.checkSchedule(Long.valueOf(cmd[2]));
            }

            default -> System.out.println("Команда не найдена");
        }
    }

    private void processTeacherCommand(String[] cmd) {
        if (cmd.length <= 1) {
            System.out.println("Некорректная команда");
            return;
        }
        switch (cmd[1]) {
            case "create" -> {
                teacherService.createTeacher(Long.valueOf(cmd[2]), cmd[3], cmd[4] + " " + cmd[5], cmd[6], cmd[7]);
                System.out.println("Учитель успешно создан...");
            }
            case "delete" -> {
                teacherService.deleteById(Long.valueOf(cmd[2]));
                System.out.println("Учитель успешно удален...");
            }
            case "find" -> {
                Teacher teacher = teacherService.findById(Long.valueOf(cmd[2]));
                System.out.println(teacher);
            }
            case "updateLogin" -> {
                teacherService.updateLogin(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Логин успешно обновлен...");
            }
            case "updateFullName" -> {
                teacherService.updateFullName(Long.valueOf(cmd[2]), cmd[3] + cmd[4]);
                System.out.println("Имя и фамилие успешно обновлено...");
            }
            case "updateSubject" -> {
                teacherService.updateSubject(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Предмет успешно обновлен...");
            }
            case "addGrade" -> {
                teacherService.addGrade(Long.valueOf(cmd[2]), Long.valueOf(cmd[3]), cmd[4], Integer.parseInt(cmd[5]), Long.valueOf(cmd[6]));
                System.out.println("Оценка успешно добавлена...");
            }
            case "deleteGrade" -> {
                teacherService.deleteGrade(Long.valueOf(cmd[2]), cmd[3], Integer.parseInt(cmd[4]), LocalDateTime.parse(cmd[5]));
                System.out.println("Оценка успешно удалена...");
            }
            case "updatePassword" -> {
                teacherService.updatePassword(Long.valueOf(cmd[2]), cmd[3]);
                System.out.println("Пароль успешно обновлен...");
            }
            case "printAll" -> {
                teacherService.printAllTeachers();
            }
            default -> System.out.println("Команда не найдена");
        }
    }

    private void processAdminCommand(String[] cmd) {
        if (cmd.length <= 1) {
            System.out.println("Некорректная команда");
            return;
        }
        switch (cmd[1]) {
            case "create" -> {
                adminService.createAdmin(Long.parseLong(cmd[2]), cmd[3], cmd[4] + " " + cmd[5], cmd[6]);
                System.out.println("Админ успешно создан...");
            }
            case "addLesson" -> {
                adminService.addLesson(Long.parseLong(cmd[2]), cmd[3], cmd[4], cmd[5], LocalDateTime.parse(cmd[6]), cmd[7]);
                System.out.println("Урок успешно создан...");
            }
            default -> System.out.println("Команда не найдена");
        }
    }
}
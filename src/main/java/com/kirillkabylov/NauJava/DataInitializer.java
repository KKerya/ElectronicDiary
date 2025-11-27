package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.database.*;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.services.AdminService;
import com.kirillkabylov.NauJava.services.StudentService;
import com.kirillkabylov.NauJava.services.TeacherService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для инициализации демо-данных при старте приложения
 */
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final UserService userService;
    private final StudentService studentService;
    private final GroupRepository groupRepository;
    private final TeacherService teacherService;
    private final SubjectRepository subjectRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository, AdminService adminService, AdminRepository adminRepository,
                           UserService userService, StudentService studentService, GroupRepository groupRepository,
                           TeacherService teacherService, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.studentService = studentService;
        this.groupRepository = groupRepository;
        this.teacherService = teacherService;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userService.createUser("TestUser1", "Alica Alisovna", "123123");
            userService.createUser("TestUser2", "Ivan Ivanov", "123123");

            Subject subject = new Subject("Math");
            subjectRepository.save(subject);
            Teacher teacher = teacherService.createTeacher("TeacherLogin", "Teacher Teacher", "123123", List.of(subject));

            Group group = new Group("11A", teacher);
            groupRepository.save(group);
            studentService.createStudent("TestStudent1", "Sasha Aleksandrov", "123123123", group);
        }
        if (adminRepository.count() == 0){
            adminService.createAdmin("TestAdmin", "Admin", "123123");
        }
    }
}

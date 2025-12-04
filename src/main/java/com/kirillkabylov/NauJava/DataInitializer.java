package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.database.*;
import com.kirillkabylov.NauJava.domain.*;
import com.kirillkabylov.NauJava.enums.AttendanceStatus;
import com.kirillkabylov.NauJava.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private final AttendanceService attendanceService;
    private final LessonRepository lessonRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository, AdminService adminService, AdminRepository adminRepository,
                           UserService userService, StudentService studentService, GroupRepository groupRepository,
                           TeacherService teacherService, SubjectRepository subjectRepository, AttendanceService attendanceService,
                           LessonRepository lessonRepository) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.studentService = studentService;
        this.groupRepository = groupRepository;
        this.teacherService = teacherService;
        this.subjectRepository = subjectRepository;
        this.attendanceService = attendanceService;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userService.createUser("TestUser1", "Alica Alisovna", "123123");
            userService.createUser("TestUser2", "Ivan Ivanov", "123123");

            Subject subject = subjectRepository.save(new Subject("Math"));
            Subject subject1 = subjectRepository.save(new Subject("Rus"));

            Teacher teacher = teacherService.createTeacher("TestTeacher1", "Teacher Teacher", "123123", List.of(subject));
            Teacher teacher2 = teacherService.createTeacher("TestTeacher2", "Teacher Teacher", "123123", List.of(subject1));


            Group group = groupRepository.save( new Group("11A", teacher));
            Student student1 = studentService.createStudent("TestStudent1", "Sasha Aleksandrov", "123123", group);
            studentService.createStudent("TestStudent2", "Masha Aleksandrova", "123123", group);

            Lesson lesson = lessonRepository.save(new Lesson(group, subject, teacher, LocalDateTime.now()));
            Lesson lesson1 = lessonRepository.save(new Lesson(group, subject, teacher, LocalDateTime.now().plusHours(100)));

            attendanceService.createAttendance(lesson, student1, AttendanceStatus.PRESENT);
            attendanceService.createAttendance(lesson1, student1, AttendanceStatus.PRESENT);
        }
        if (adminRepository.count() == 0){
            adminService.createAdmin("TestAdmin", "Admin", "123123");
        }
    }
}

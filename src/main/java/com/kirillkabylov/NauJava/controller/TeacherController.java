package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.domain.UserEntity;
import com.kirillkabylov.NauJava.dto.MakeTeacherRequest;
import com.kirillkabylov.NauJava.services.TeacherService;
import com.kirillkabylov.NauJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {
    private final UserService userService;
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(UserService userService,
                             TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    /**
     * Сделать пользователя учителем по его логину
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/make")
    public String makeTeacher(@RequestBody MakeTeacherRequest request) {
        UserEntity user = userService.getByLogin(request.login());

        if (user instanceof Teacher) {
            return "Пользователь уже является учителем";
        }

        teacherService.promoteToTeacher(user, request.subjectIds());
        return "Пользователь " + request.login() + " успешно назначен учителем";
    }
}

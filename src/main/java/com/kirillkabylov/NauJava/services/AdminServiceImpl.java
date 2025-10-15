package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.AdminRepository;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Admin;
import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, LessonRepository lessonRepository) {
        this.adminRepository = adminRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void createAdmin(long id, String login, String fullName, String password) {
        adminRepository.create(new Admin(id, login, fullName, password));
    }

    @Override
    public void addLesson(long id, String groupName, String subject, String teacher, LocalDateTime startTime, String room) {
        lessonRepository.create(new Lesson(id, groupName, subject, teacher, startTime, room));
    }
}
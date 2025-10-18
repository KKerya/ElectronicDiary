package com.kirillkabylov.NauJava.services;

import java.time.LocalDateTime;

public interface AdminService {
    void createAdmin(long id, String login, String fullName, String password);

    void addLesson(long id, String groupName, String subject, String teacher, LocalDateTime startTime, String room);
}

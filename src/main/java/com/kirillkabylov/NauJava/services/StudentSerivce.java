package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;

public interface StudentSerivce {
    void createStudent(Long id, String login, String fullName, String password, String groupName);

    Student findById(Long id);

    void deleteById(Long id);

    void updateLogin(Long id, String newLogin);

    void updatePassword(Long id, String newPassword);

    void updateFullName(Long id, String newName);

    void updateGroupName(Long id, String newGroupName);
}

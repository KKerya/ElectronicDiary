package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();

    Group getById(Long id);
}

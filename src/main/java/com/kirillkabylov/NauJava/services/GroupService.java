package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> getAllGroups();

    Group getById(Long id);
}

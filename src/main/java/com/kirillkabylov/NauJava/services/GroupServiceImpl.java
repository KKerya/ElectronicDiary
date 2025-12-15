package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group with id - " + id + "not found"));
    }

    @Override
    public Group createGroup(String name) {
        return groupRepository.save(new Group(name));
    }

    @Override
    public Group createGroup(String name, Teacher teacher) {
        return groupRepository.save(new Group(name, teacher));
    }
}

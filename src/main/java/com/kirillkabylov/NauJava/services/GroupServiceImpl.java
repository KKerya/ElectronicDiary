package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.domain.Group;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups(){
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Group getById(Long id){
        return groupRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Group with id - " + id + "not found"));
    }
}

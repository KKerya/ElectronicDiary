package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.domain.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return (List<Subject>) subjectRepository.findAll();
    }

    @Override
    public List<Subject> getSubjectsByTeacherLogin(String login) {
        return subjectRepository.findByTeacherLogin(login);
    }

    @Override
    public Subject getById(Long subjectId){
        return subjectRepository.findById(subjectId).orElseThrow( () -> new EntityNotFoundException("Subject with id " + subjectId + " not found"));
    }

    @Override
    public List<Subject> findSubjectByIds(List<Long> subjectsIds){
        return subjectRepository.findAllById(subjectsIds);
    }
}

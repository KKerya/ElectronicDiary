package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Subject> getById(Long subjectId){
        return subjectRepository.findById(subjectId);
    }
}

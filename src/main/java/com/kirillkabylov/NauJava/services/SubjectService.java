package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Optional<Subject> getById(Long subjectId);
}

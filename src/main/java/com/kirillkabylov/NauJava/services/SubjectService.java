package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllSubjects();

    List<Subject> getSubjectsByTeacherLogin(String login);

    Subject getById(Long subjectId);

    List<Subject> findSubjectByIds(List<Long> subjectsIds);

    Subject createSubject(String name);

    void setSubjectTeacher(Long subjectId, Long teacherId);

    void removeSubjectFromTeacher(Long subjectId, Long teacherId);
}

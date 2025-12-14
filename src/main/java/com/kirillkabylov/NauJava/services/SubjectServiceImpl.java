package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.SubjectRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
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
    public Subject getById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id " + subjectId + " not found"));
    }

    @Override
    public List<Subject> findSubjectByIds(List<Long> subjectsIds) {
        return subjectRepository.findAllById(subjectsIds);
    }

    @Override
    public Subject createSubject(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("Название предмета пустое");
        }
        if (subjectRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Предмет с именем - " + name + " уже существует");
        }
        return subjectRepository.save(new Subject(name));
    }

    @Override
    public void setSubjectTeacher(Long subjectId, Long teacherId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        if(teacher.getSubjects().stream()
                .anyMatch(s -> s.getId().equals(subjectId))){
            throw new RuntimeException("Данный учитель уже назначен на этот предмет");
        }
        subject.getTeachers().add(teacher);
        teacher.getSubjects().add(subject);
        subjectRepository.save(subject);
    }

    @Override
    public void removeSubjectFromTeacher(Long subjectId, Long teacherId){
        Subject subject = subjectRepository.getReferenceById(subjectId);
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        if(teacher.getSubjects().stream()
                .noneMatch(s -> s.getId().equals(subjectId))){
            throw new RuntimeException("Данный учитель не назначен на этот предмет");
        }
        teacher.getSubjects().remove(subject);
        teacherRepository.save(teacher);
    }
}

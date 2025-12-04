package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> getLessonsByTeacherLogin(String login){
        return lessonRepository.findByTeacherLogin(login);
    }

    @Override
    public Lesson getLessonById(Long lessonId){
        return lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson with id - " + lessonId + " not found"));
    }
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LessonRepository implements CrudRepository<Lesson, Long> {
    private final List<Lesson> lessonContainer;

    public LessonRepository(List<Lesson> lessonContainer) {
        this.lessonContainer = lessonContainer;
    }

    public Lesson create(Lesson entity) {
        lessonContainer.add(entity);
        return entity;
    }

    @Override
    public Optional<Lesson> read(Long id) {
        return lessonContainer.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public Lesson update(Lesson entity) {
        for (int i = 0; i < lessonContainer.size(); i++) {
            if (lessonContainer.get(i).getId().equals(entity.getId())) {
                lessonContainer.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return lessonContainer.removeIf(x -> x.getId().equals(id));
    }

    public List<Lesson> findAll() {
        return lessonContainer;
    }

    public List<Lesson> findByGroupName(String groupName) {
        return lessonContainer.stream()
                .filter(lesson -> lesson.getGroupName().equalsIgnoreCase(groupName))
                .collect(Collectors.toList());
    }
}
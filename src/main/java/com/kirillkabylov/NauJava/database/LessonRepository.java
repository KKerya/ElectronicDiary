package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonRepository implements CrudRepository<Lesson, Long> {
    private final List<Lesson> lessonContainer;

    public LessonRepository(List<Lesson> lessonContainer) {
        this.lessonContainer = lessonContainer;
    }

    public void create(Lesson entity) {
        lessonContainer.add(entity);
    }

    @Override
    public Lesson read(Long id) {
        return lessonContainer.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Lesson entity) {
        for (int i = 0; i < lessonContainer.size(); i++) {
            if (lessonContainer.get(i).getId().equals(entity.getId())) {
                lessonContainer.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        lessonContainer.removeIf(x -> x.getId().equals(id));
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
package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradeRepository implements CrudRepository<Grade, Long> {
    private final List<Grade> gradeContainer = new ArrayList<>();

    public void create(Grade entity) {
        gradeContainer.add(entity);
    }

    @Override
    public Grade read(Long id) {
        return gradeContainer.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Grade entity) {
        for (int i = 0; i < gradeContainer.size(); i++) {
            if (gradeContainer.get(i).getId().equals(entity.getId())) {
                gradeContainer.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        gradeContainer.removeIf(x -> x.getId().equals(id));
    }

    public List<Grade> findAll() {
        return gradeContainer;
    }

}

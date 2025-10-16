package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GradeRepository implements CrudRepository<Grade, Long> {
    private final List<Grade> gradeContainer;

    @Autowired
    public GradeRepository(List<Grade> gradeContainer) {
        this.gradeContainer = gradeContainer;
    }

    public Grade create(Grade entity) {
        gradeContainer.add(entity);
        return entity;
    }

    @Override
    public Optional<Grade> read(Long id) {
        return gradeContainer.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public Grade update(Grade entity) {
        for (int i = 0; i < gradeContainer.size(); i++) {
            if (gradeContainer.get(i).getId().equals(entity.getId())) {
                gradeContainer.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return gradeContainer.removeIf(x -> x.getId().equals(id));
    }

    public List<Grade> findAll() {
        return gradeContainer;
    }

}

package com.kirillkabylov.NauJava.database;

import java.util.List;
import java.util.ArrayList;
import com.kirillkabylov.NauJava.domain.User;

public abstract class MemoryRepository<T extends User> implements CrudRepository<T, Long> {
    private List<T> storage;

    public MemoryRepository(List<T> storage){
        this.storage = storage;
    }

    @Override
    public void create(T entity){
        storage.add(entity);
    }

    @Override
    public T read(Long id) {
        return storage.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(T entity) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId().equals(entity.getId())) {
                storage.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        storage.removeIf(x -> x.getId().equals(id));
    }

    public List<T> findAll() {
        return storage;
    }
}
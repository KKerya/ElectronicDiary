package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.User;

import java.util.List;
import java.util.Optional;

public abstract class MemoryRepository<T extends User> implements CrudRepository<T, Long> {
    private final List<T> storage;

    public MemoryRepository(List<T> storage) {
        this.storage = storage;
    }

    @Override
    public T create(T entity) {
        storage.add(entity);
        return entity;
    }

    @Override
    public Optional<T> read(Long id) {
        return storage.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public T update(T entity) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId().equals(entity.getId())) {
                storage.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return storage.removeIf(x -> x.getId().equals(id));
    }

    public List<T> findAll() {
        return storage;
    }
}
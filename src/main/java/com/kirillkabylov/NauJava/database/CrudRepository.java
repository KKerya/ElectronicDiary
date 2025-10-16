package com.kirillkabylov.NauJava.database;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T create(T entity);

    Optional<T> read(ID id);

    T update(T entity);

    boolean delete(ID id);
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Homework;
import org.springframework.data.repository.CrudRepository;

public interface HomeworkRepository extends CrudRepository<Homework, Long> {
}

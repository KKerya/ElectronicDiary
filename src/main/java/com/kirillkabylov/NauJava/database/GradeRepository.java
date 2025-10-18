package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Long> {
}

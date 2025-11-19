package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
}

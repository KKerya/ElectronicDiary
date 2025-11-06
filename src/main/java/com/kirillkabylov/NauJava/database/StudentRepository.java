package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface StudentRepository extends CrudRepository<Student, Long> {
    /**
     * Находит студента по логину
     * @param login логин
     */
    Optional<Student> findByLogin(String login);
}

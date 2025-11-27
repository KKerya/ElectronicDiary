package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * Находит студента по логину
     *
     * @param login логин
     */
    Optional<Student> findByLogin(String login);
}

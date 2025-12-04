package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Subject;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT subject FROM Subject subject WHERE subject.teacher.login = :login")
    List<Subject> findByTeacherLogin(String login);
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    Optional<Grade> findByStudentAndSubjectAndValueAndDate(
            Student student,
            String subject,
            int value,
            LocalDateTime date
    );

    /**
     * Удаляет все оценки студента
     * @param student студент
     */
    @Query("DELETE FROM Grade grade WHERE grade.student = :student")
    void deleteAllByStudent(@Param("student") Student student);
}

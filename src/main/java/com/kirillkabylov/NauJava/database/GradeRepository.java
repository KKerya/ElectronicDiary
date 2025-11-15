package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface GradeRepository extends CrudRepository<Grade, Long> {
    /**
     * Находит оценку по Студенту, предмету, оценке и дате
     * @param studentId id студента
     * @param subject предмет
     * @param value оценка
     * @param date время
     */
    Optional<Grade> findByStudentIdAndSubjectAndValueAndDate(
            Long studentId,
            String subject,
            int value,
            LocalDateTime date
    );

    /**
     * Удаляет все оценки студента
     * @param student студент
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Grade grade WHERE grade.student = :student")
    void deleteAllByStudent(@Param("student") Student student);

    /**
     * Находит все оценки для заданного студента
     * @param student студент, для которого ищем оценки
     * @return список всех оценок студента
     */
    List<Grade> findAllByStudent(Student student);

    /**
     * Находит все оценки для заданного студента
     * @param studentId id студента, для которого ищем оценки
     * @return список всех оценок студента
     */
    @Query("SELECT grade FROM Grade grade WHERE grade.student.id = :studentId")
    List<Grade> findAllByStudentId(Long studentId);
}

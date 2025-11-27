package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Grade;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    /**
     * Находит оценку по Студенту, предмету, оценке и дате
     *
     * @param studentId id студента
     * @param subjectId id предмета
     * @param value     оценка
     * @param date      время
     */
    Optional<Grade> findByStudentIdAndSubjectIdAndValueAndDate(
            Long studentId,
            Long subjectId,
            int value,
            LocalDateTime date
    );

    /**
     * Удаляет все оценки студента
     *
     * @param student студент
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Grade grade WHERE grade.student = :student")
    void deleteAllByStudent(@Param("student") Student student);

    /**
     * Находит все оценки для заданного студента
     *
     * @param student студент, для которого ищем оценки
     * @return список всех оценок студента
     */
    List<Grade> findAllByStudent(Student student);

    /**
     * Находит все оценки для заданного студента
     *
     * @param studentId id студента, для которого ищем оценки
     * @return список всех оценок студента
     */
    @Query("SELECT grade FROM Grade grade WHERE grade.student.id = :studentId")
    List<Grade> findAllByStudentId(Long studentId);

    @Query("SELECT grade FROM Grade grade WHERE grade.student.login = :studentLogin AND grade.subject.id = :subjectId")
    List<Grade> findAllByStudentLoginAndSubjectId(String studentLogin,
                                                  Long subjectId);

    @Query("SELECT grade FROM Grade grade WHERE grade.subject.id = :subjectId AND grade.student.group.id = :groupId")
    List<Grade> findAllBySubjectIdAndGroup(Long subjectId, Long groupId);

    List<Grade> findAllByStudentGroupId(Long groupId);

    @Query("SELECT grade FROM Grade grade WHERE grade.student.login = :login")
    List<Grade> findAllByStudentLogin(String login);

    @Query("SELECT grade FROM Grade grade Where grade.student.group.id = :groupId AND grade.date BETWEEN :start AND :end")
    List<Grade> findGradesByGroupAndDate(
            Long groupId,
            LocalDateTime start,
            LocalDateTime end);
}

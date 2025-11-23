package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    /**
     * Находит всех учителей с заданным именем и предметом
     *
     * @param fullName    имя учителя
     * @param subjectName название предмета
     */
    @Query("SELECT t FROM Teacher t JOIN t.subjects s WHERE t.fullName = :fullName AND s.name = :subjectName")
    List<Teacher> findByFullNameAndSubject(@Param("fullName") String fullName, @Param("subjectName") String subjectName);

    /**
     * Находит всех учителей с заданным предметом
     *
     * @param subjectName название предмета
     */
    @Query("SELECT t FROM Teacher t JOIN t.subjects s WHERE s.name = :subjectName")
    List<Teacher> findBySubject(@Param("subjectName") String subjectName);

    /**
     * Находит всех учителей с заданным именем
     *
     * @param fullName имя учителя
     */
    List<Teacher> findByFullName(String fullName);

    /**
     * Находит учителя по логину
     *
     * @param login логин
     */
    Optional<Teacher> findByLogin(String login);
}

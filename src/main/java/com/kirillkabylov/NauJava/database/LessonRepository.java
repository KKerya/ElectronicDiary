package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    /**
     * Находит все занятии для учителя с заданным именем
     *
     * @param fullName имя учителя
     */
    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.teacher.fullName = :fullName")
    List<Lesson> findLessonByTeacherName(@Param("fullName") String fullName);

    @EntityGraph(attributePaths = {"attendances"})
    List<Lesson> findByGroupIdAndSubjectId(Long groupId, Long subjectId);

    List<Lesson> findByTeacherLogin(String Login);

    @Query("SELECT l FROM Lesson l WHERE l.group.id = :groupId AND FUNCTION('DATE', l.startTime) BETWEEN :startDate AND :endDate")
    List<Lesson> findByGroupIdAndStartTimeBetween(Long groupId, LocalDateTime startDate, LocalDateTime endDate);
}
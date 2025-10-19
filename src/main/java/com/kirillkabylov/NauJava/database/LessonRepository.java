package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    /**
     * Находит все занятии для учителя с заданным именем
     * @param fullName имя учителя
     */
    @Query("SELECT lesson FROM Lesson lesson WHERE lesson.teacher.fullName = :fullName")
    List<Lesson> findCoursesByTeacherName(@Param("fullName") String fullName);
}
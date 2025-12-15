package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findByLessonGroupStudentsIdAndLessonSubjectId(Long studentId, Long subjectId);
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LessonRepositoryImpl implements LessonRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public LessonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Lesson> findLessonByTeacherName(String fullName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);

        Root<Lesson> lessonRoot = criteriaQuery.from(Lesson.class);
        Join<Lesson, Teacher> teacherJoin = lessonRoot.join("teacher", JoinType.INNER);

        Predicate namePredicate = criteriaBuilder.equal(teacherJoin.get("fullName"), fullName);
        criteriaQuery.select(lessonRoot).where(namePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherRepositoryImpl implements TeacherRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public TeacherRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Teacher> findByField(String field, String value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
        Root<Teacher> root = cq.from(Teacher.class);
        Predicate predicate = cb.equal(root.get(field), value);

        cq.select(root).where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }
}
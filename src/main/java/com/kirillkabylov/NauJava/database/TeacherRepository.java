package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    /**
     * Находит всех учителей с заданным именем и предметом
     * @param fullName имя учителя
     * @param subject название предмета
     */
    List<Teacher> findByFullNameAndSubject(String fullName, String subject);

    /**
     * Находит всех учителей с заданным предметом
     * @param subject название предмета
     */
    List<Teacher> findBySubject(String subject);

    /**
     * Находит всех учителей с заданным именем
     * @param fullName имя учителя
     */
    List<Teacher> findByFullName(String fullName);
}

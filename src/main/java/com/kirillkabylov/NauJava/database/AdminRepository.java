package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AdminRepository extends CrudRepository<Admin, Long> {
    /**
     * Находит админа по логину
     * @param login логин
     */
    Optional<Admin> findByLogin(String login);
}

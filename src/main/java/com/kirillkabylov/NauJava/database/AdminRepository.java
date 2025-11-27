package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    /**
     * Находит админа по логину
     *
     * @param login логин
     */
    Optional<Admin> findByLogin(String login);
}

package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * находит всех пользователей с заданным именем
     * @param fullName
     */
    List<UserEntity> findByFullName(String fullName);

    /**
     * Находит пользователя по логину
     * @param login логин
     */
    Optional<UserEntity> findByLogin(String login);
}

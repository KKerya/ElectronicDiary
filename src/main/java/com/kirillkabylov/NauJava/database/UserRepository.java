package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * находит всех пользователей с заданным именем
     * @param fullName
     * @return
     */
    List<UserEntity> findByFullName(String fullName);
}

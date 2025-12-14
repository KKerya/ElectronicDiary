package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * создание пользователя
     * @param login логин
     * @param fullName полное имя
     * @param password пароль
     */
    public void createUser(String login, String fullName, String password);

    void createUser(UserEntity user);

    /**
     * Находит пользователей по имени
     * @param fullname полное имя
     */
    public List<UserEntity> findByFullName(String fullname);

    /**
     * Находит пользователя по логину
     * @param login логин
     */
    UserEntity getByLogin(String login);

    void deleteUser(UserEntity user);

    void changePassword(String login, String newPassword);
}

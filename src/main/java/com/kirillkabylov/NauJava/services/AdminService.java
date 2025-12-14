package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.domain.Admin;

public interface AdminService {
    /**
     * Создает админа
     *
     * @param login    логин
     * @param fullName полное имя (ФИО)
     * @param password пароль
     */
    void createAdmin(String login, String fullName, String password);

    /**
     * Удаляет админа
     *
     * @param admin ссылка на админа
     */
    void deleteAdmin(Admin admin);

    /**
     * Удаляет админа
     *
     * @param id id админа
     */
    void deleteAdmin(long id);
}

package com.kirillkabylov.NauJava.domain;

public class Admin extends User {
    public Admin(long id, String login, String fullName, String password) {
        super(id, login, fullName, password);
    }
}
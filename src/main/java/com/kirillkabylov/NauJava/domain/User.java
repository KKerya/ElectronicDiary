package com.kirillkabylov.NauJava.domain;

public abstract class User {
    private Long id;
    private String login;
    private String fullName;
    private String password;

    public User() {
    }

    public User(long id, String login, String fullName, String password) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

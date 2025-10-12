package com.kirillkabylov.NauJava.model;

public abstract class User {
    private long id;
    private String login;
    private String fullName;
    private String password;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}

package com.kirillkabylov.NauJava.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    private Subject subject;

    public Teacher(){}

    public Teacher(long id, String login, String fullName, String password, Subject subject) {
        super(id, login, fullName, password);
        this.subject = subject;
    }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

}

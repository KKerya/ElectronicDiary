package com.kirillkabylov.NauJava.domain;

public class Teacher extends User {
    private String subject;

    public Teacher() {
    }

    public Teacher(long id, String login, String fullName, String password, String subject) {
        super(id, login, fullName, password);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return getFullName() + " ??????????????: " + subject;
    }

}

package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_teachers")
public class Teacher extends User {
    @Column
    private String subject;

    public Teacher() {
    }

    public Teacher(String login, String fullName, String password, String subject) {
        super(login, fullName, password);
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
        return getFullName() + " Предмет: " + subject;
    }

}

package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends UserEntity {
    @OneToMany(cascade = {CascadeType.MERGE})
    private List<Subject> subjects;

    public Teacher() {
    }

    public Teacher(String login, String fullName, String password) {
        super(login, fullName, password);
    }

    public Teacher(String login, String fullName, String password, List<Subject> subjects) {
        super(login, fullName, password);
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", login='" + getLogin() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", subject='" + getSubjects() + '\'' +
                '}';
    }
}

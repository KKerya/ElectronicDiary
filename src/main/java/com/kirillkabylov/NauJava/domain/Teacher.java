package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends UserEntity {
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.MERGE)
    private List<Subject> subjects = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(String login, String fullName, String password) {
        super(login, fullName, password);
    }

    public Teacher(String login, String fullName, String password, List<Subject> subjects) {
        super(login, fullName, password);
        this.subjects = subjects == null ? new ArrayList<>() : new ArrayList<>(subjects);
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

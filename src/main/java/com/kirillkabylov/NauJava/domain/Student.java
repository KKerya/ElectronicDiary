package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_students")
public class Student extends UserEntity {
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Student() {
    }

    public Student(String login, String fullName, String password, Group group) {
        super(login, fullName, password);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", login='" + getLogin() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", group='" + getGroup() + '\'' +
                '}';
    }
}

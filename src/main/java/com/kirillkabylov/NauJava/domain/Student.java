package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_students")
public class Student extends User {
    @Column
    private String groupName;

    public Student() {
    }

    public Student(String login, String fullName, String password, String groupName) {
        super(login, fullName, password);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return getFullName() + " " + groupName;
    }
}

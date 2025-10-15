package com.kirillkabylov.NauJava.domain;


public class Student extends User {
    private String groupName;

    public Student() {
    }

    public Student(long id, String login, String fullName, String password, String groupName) {
        super(id, login, fullName, password);
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

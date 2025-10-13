package com.kirillkabylov.NauJava.domain;

import java.time.LocalDateTime;

public class Grade {
    private Long id;
    private Integer value;

    private Student student;
    private Subject subject;

    private Teacher teacher;
    private LocalDateTime date;

    public Grade() {
    }

    public Grade(Student student, Subject subject, Teacher teacher, int value, LocalDateTime date) {
        this.value = value;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}

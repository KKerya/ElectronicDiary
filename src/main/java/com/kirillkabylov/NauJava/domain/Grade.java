package com.kirillkabylov.NauJava.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Grade {
    private Long id;
    private Integer value;

    private Student student;
    private String subject;

    private Teacher teacher;
    private LocalDateTime date;

    public Grade() {
    }

    public Grade(Long id, int value, Student student, String subject, Teacher teacher, LocalDateTime date) {
        this.id = id;
        this.value = value;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return subject + ": " + value + "\n" + "Преподаватель: " + teacher + " Время: " + date + "(" + date.format(formatter) + ")";
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
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

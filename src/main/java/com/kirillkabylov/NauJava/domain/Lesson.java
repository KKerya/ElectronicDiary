package com.kirillkabylov.NauJava.domain;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public class Lesson {
    private Long id;
    private String groupName;
    private String subject;
    private String teacher;
    private LocalDateTime startTime;
    @Value("${lesson.max-duration:45}")
    private int durationMinutes;
    private String room;

    public Lesson(Long id, String groupName, String subject, String teacher, LocalDateTime startTime, String room){
        this.id = id;
        this.groupName = groupName;
        this.subject = subject;
        this.teacher = teacher;
        this.startTime = startTime;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getRoom() {
        return room;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
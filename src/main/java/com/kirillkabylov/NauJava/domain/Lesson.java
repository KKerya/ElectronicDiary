package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String groupName;

    @Column
    private String subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homework> homeworkList = new ArrayList<>();

    @Value("${lesson.max-duration:45}")
    private int durationMinutes;

    public Lesson() {
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher=" + teacher +
                ", startTime=" + startTime +
                ", durationMinutes=" + durationMinutes +
                '}';
    }

    public Lesson(String groupName, String subject, Teacher teacher, LocalDateTime startTime) {
        this.groupName = groupName;
        this.subject = subject;
        this.teacher = teacher;
        this.startTime = startTime;
    }

    public void addHomework(Homework homework) {
        homeworkList.add(homework);
        homework.setLesson(this);
    }

    public void removeHomework(Homework homework) {
        homeworkList.remove(homework);
        homework.setLesson(null);
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

    public Teacher getTeacher() {
        return teacher;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<Homework> getHomeworkList() { return homeworkList; }
}
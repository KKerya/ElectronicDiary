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

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homework> homeworkList = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();

    @Value("${lesson.max-duration:45}")
    private int durationMinutes;

    public Lesson() {
    }

    public Lesson(Group group, Subject subject, Teacher teacher, LocalDateTime startTime) {
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", groupName='" + group.getName() + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher=" + teacher +
                ", startTime=" + startTime +
                ", durationMinutes=" + durationMinutes +
                '}';
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

    public Group getGroup() {
        return group;
    }

    public Subject getSubject() {
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

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<Homework> getHomeworkList() { return homeworkList; }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }
}
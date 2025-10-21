package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_groups")
public class Group {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;

    @Column
    String groupName;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Student> students = new ArrayList<>();

    public Group(){
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, Teacher teacher) {
        this.groupName = groupName;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public Teacher teacher(){
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}

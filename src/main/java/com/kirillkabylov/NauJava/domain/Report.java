package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum ReportStatus {
        CREATED, COMPLETED, ERROR
    }

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Report() {
        this.status = ReportStatus.CREATED;
    }

    public Long getId(){
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}

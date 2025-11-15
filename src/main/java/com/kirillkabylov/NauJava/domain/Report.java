package com.kirillkabylov.NauJava.domain;

import jakarta.persistence.*;

@Entity
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

    public Report() {}

    public Report(ReportStatus status) {
        this.status = status;
    }
}

package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.ReportRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.domain.Report;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.dto.ReportDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Сервис для работы с отчетами.
 * Предоставляет методы для создания отчета, получения его содержимого
 * и асинхронного формирования отчета с использованием CompletableFuture.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    @Override
    public Long createReport() {
        Report report = new Report();
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public String getReportContent(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Отчет не найден"));
        return switch (report.getStatus()) {
            case Report.ReportStatus.COMPLETED -> report.getContent();
            case Report.ReportStatus.CREATED -> "Отчет формируется";
            case Report.ReportStatus.ERROR -> "Отчет сформировался с ошибкой";
        };
    }

    @Override
    public CompletableFuture<ReportDto> generateReportAsync(Long reportId) {
        return CompletableFuture.supplyAsync(() -> {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Отчет не найден"));
            long totalStartTime = System.currentTimeMillis();
            try {
                final long[] userCount = new long[1];
                final long[] timeUsers = new long[1];
                Thread userThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    userCount[0] = userRepository.count();
                    timeUsers[0] = System.currentTimeMillis() - start;
                });

                final List<Student>[] studentsList = new List[1];
                final long[] timeStudents = new long[1];
                Thread studentThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    studentsList[0] = (List<Student>) studentRepository.findAll();
                    timeStudents[0] = System.currentTimeMillis() - start;
                });

                userThread.start();
                studentThread.start();

                try {
                    userThread.join();
                    studentThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    report.setStatus(Report.ReportStatus.ERROR);
                    reportRepository.save(report);
                    return null;
                }

                long totalElapsed = System.currentTimeMillis() - totalStartTime;

                String content = String.format(
                        "users:%d;students:%d;timeUsers:%d;timeStudents:%d;totalTime:%d",
                        userCount[0],
                        studentsList[0].size(),
                        timeUsers[0],
                        timeStudents[0],
                        totalElapsed
                );

                report.setContent(content);
                report.setStatus(Report.ReportStatus.COMPLETED);
                reportRepository.save(report);

                final ReportDto reportDto = new ReportDto(
                        userCount[0],
                        studentsList[0],
                        timeUsers[0],
                        timeStudents[0],
                        totalElapsed
                );
                return reportDto;
            } catch (Exception e) {
                report.setStatus(Report.ReportStatus.ERROR);
                reportRepository.save(report);
            }
            return null;
        });
    }
}

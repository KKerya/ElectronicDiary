package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.dto.ReportDto;
import jakarta.transaction.Transactional;

import java.util.concurrent.CompletableFuture;

public interface ReportService {
    @Transactional
    Long createReport();

    String getReportContent(Long reportId);

    CompletableFuture<ReportDto> generateReportAsync(Long reportId);
}

package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.dto.ReportDto;
import jakarta.transaction.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для работы с отчетами.
 * Отвечает за создание, генерацию и обработку отчетов.
 */
public interface ReportService {

    /**
     * Создает новый отчет.
     *
     * @return id созданного отчета
     */
    @Transactional
    Long createReport();

    /**
     * Получает содержимое отчета по его id.
     *
     * @param reportId id отчета
     * @return содержимое отчета
     */
    String getReportContent(Long reportId);

    /**
     * Асинхронно генерирует отчет.
     *
     * @param reportId id отчета
     * @return {@link CompletableFuture} с DTO отчета
     */
    CompletableFuture<ReportDto> generateReportAsync(Long reportId);

    /**
     * Парсит содержимое отчета в DTO.
     *
     * @param content строковое содержимое отчета
     * @return DTO отчета
     */
    ReportDto parseReportContent(String content);
}

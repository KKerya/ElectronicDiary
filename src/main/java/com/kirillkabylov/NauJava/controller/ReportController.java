package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.ReportDto;
import com.kirillkabylov.NauJava.services.ReportService;
import com.kirillkabylov.NauJava.services.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для работы с отчетами.
 * <p>
 * Предоставляет возможность создавать отчет, формировать его асинхронно
 * и отображать результат в Thymeleaf-шаблоне.
 * </p>
 */
@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report/start")
    public String startReport(Model model) {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId); // Запуск без .get()
        model.addAttribute("reportId", reportId);
        return "reportStarted";
    }

    /**
     *  Обрабатывает GET-запрос на отображение отчета.
     */
    @GetMapping("/report/view")
    public String viewReport(@RequestParam("id") Long reportId, Model model) throws InterruptedException {

        ReportDto reportDto;

        try {
            String content = reportService.getReportContent(reportId);

            if (!content.contains(";")) {
                model.addAttribute("message", content);
                model.addAttribute("reportId", reportId);
                return "reportStatus";
            }

            reportDto = reportService.parseReportContent(content);

        } catch (Exception e) {
            model.addAttribute("error", "Не удалось получить отчет");
            model.addAttribute("reportId", reportId);
            return "reportStatus";
        }

        model.addAttribute("userCount", reportDto.userCount());
        model.addAttribute("students", reportDto.students());
        model.addAttribute("timeUsers", reportDto.timeUsers());
        model.addAttribute("timeEntities", reportDto.timeEntities());
        model.addAttribute("totalTime", reportDto.totalTime());

        return "report"; // нормальная страница с отчетом
    }
}

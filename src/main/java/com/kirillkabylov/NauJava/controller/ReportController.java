package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.ReportDto;
import com.kirillkabylov.NauJava.services.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для работы с отчетами.
 * <p>
 * Предоставляет возможность создавать отчет, формировать его асинхронно
 * и отображать результат в Thymeleaf-шаблоне.
 * </p>
 */
@Controller
public class ReportController {
    private final ReportServiceImpl reportServiceImpl;

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    /**
     *  Обрабатывает GET-запрос на отображение отчета.
     */
    @GetMapping("/view/report")
    public String viewReport(Model model) {


        Long reportId = reportServiceImpl.createReport();

        ReportDto reportDto;
        try {
            reportDto = reportServiceImpl.generateReportAsync(reportId).get();
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось сформировать отчет");
            return "reportView";
        }

        if (reportDto == null) {
            model.addAttribute("error", "Отчет не был сформирован");
            return "reportView";
        }

        model.addAttribute("userCount", reportDto.userCount());
        model.addAttribute("students", reportDto.students());
        model.addAttribute("timeUsers", reportDto.timeUsers());
        model.addAttribute("timeEntities", reportDto.timeEntities());
        model.addAttribute("totalTime", reportDto.totalTime());

        return "report";
    }
}

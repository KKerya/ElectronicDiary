package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.dto.WeekDto;
import com.kirillkabylov.NauJava.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class SchedulePageController {
    private final StudentService studentService;

    @Autowired
    public SchedulePageController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/page")
    public String getSchedulePage(Model model,  @AuthenticationPrincipal UserDetails user) {
        long groupId = studentService.getByLogin(user.getUsername()).getGroup().getId();

        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 9, 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear() + 1, 5, 31);

        List<WeekDto> weeks = new ArrayList<>();
        WeekFields wf = WeekFields.ISO;

        LocalDate date = start;
        int weekNumber = 1;
        while (date.isBefore(end)) {
            weeks.add(new WeekDto(weekNumber, "Неделя " + weekNumber));
            date = date.plusWeeks(1);
            weekNumber++;
        }

        model.addAttribute("weeks", weeks);
        model.addAttribute("groupId", groupId);
        return "schedule";
    }
}

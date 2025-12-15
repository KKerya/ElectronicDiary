package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.dto.LessonForScheduleDto;
import com.kirillkabylov.NauJava.dto.WeekScheduleDto;
import com.kirillkabylov.NauJava.services.LessonService;
import com.kirillkabylov.NauJava.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/schedule")
public class ScheduleController {
    private final LessonService lessonService;
    private final StudentService studentService;

    @Autowired
    public ScheduleController(LessonService lessonService, StudentService studentService) {
        this.lessonService = lessonService;
        this.studentService = studentService;
    }

    /**
     * Получить расписание на неделю
     *
     * @param groupId    id группы
     * @param weekNumber номер недели
     * @param user       пользователь
     */
    @GetMapping("/group/{groupId}/week/{weekNumber}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public WeekScheduleDto getGroupWeekSchedule(
            @PathVariable Long groupId,
            @PathVariable int weekNumber,
            @AuthenticationPrincipal UserDetails user
    ) {
        boolean isStudent = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));

        if (isStudent) {
            Long studentGroupId = studentService.getByLogin(user.getUsername()).getGroup().getId();
            if (!studentGroupId.equals(groupId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Доступ запрещен: не ваша группа");
            }
        }

        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 9, 1);
        LocalDate monday = startOfYear.plusWeeks(weekNumber - 1).with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);

        List<Lesson> lessons = lessonService.getLessonsByGroupIdBetweenDates(groupId, monday, sunday);

        Map<DayOfWeek, List<LessonForScheduleDto>> lessonsByDay = lessons.stream()
                .filter(l -> l.getStartTime() != null)
                .collect(Collectors.groupingBy(
                        l -> l.getStartTime().getDayOfWeek(),
                        Collectors.mapping(l -> new LessonForScheduleDto(
                                l.getSubject().getName(),
                                l.getStartTime().toString(),
                                l.getDurationMinutes()
                        ), Collectors.toList())
                ));


        return new WeekScheduleDto(weekNumber, lessonsByDay);
    }

}

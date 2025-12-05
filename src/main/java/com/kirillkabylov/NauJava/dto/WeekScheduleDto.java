package com.kirillkabylov.NauJava.dto;

import com.kirillkabylov.NauJava.domain.Lesson;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record WeekScheduleDto(
        int weekNumber,
        Map<DayOfWeek, List<LessonForScheduleDto>> lessonsByDay
) {}
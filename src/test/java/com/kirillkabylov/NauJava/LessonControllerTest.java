package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.controller.LessonController;
import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Teacher;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
public class LessonControllerTest {
    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonController lessonController;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(lessonController);
    }

    @Test
    void getLessonsTeacherExists() {
        Teacher teacher = new Teacher("teacher", "Name Surname", "pass", "Math");
        Lesson lesson1 = new Lesson("Group A", "Math", teacher, LocalDateTime.now());
        Lesson lesson2 = new Lesson("Group B", "Physics", teacher, LocalDateTime.now().plusHours(1));

        when(lessonRepository.findLessonByTeacherName("Name Surname"))
                .thenReturn(List.of(lesson1, lesson2));

        MockMvcResponse response = RestAssuredMockMvc
                .given()
                .param("name", "Name Surname")
                .get("/custom/lessons");

        assertEquals(200, response.getStatusCode());
        assertEquals(2, response.jsonPath().getList("$").size(), "Должно быть 2 урока");
    }

    @Test
    void getLessonsTeacherNotFound() {
        when(lessonRepository.findLessonByTeacherName("Unknown Teacher"))
                .thenReturn(List.of());

        MockMvcResponse response = RestAssuredMockMvc
                .given()
                .param("name", "Unknown Teacher")
                .get("/custom/lessons");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("$").isEmpty(), "Список уроков должен быть пустым");
    }

    @Test
    void getLessonsEmptyName() {
        MockMvcResponse response = RestAssuredMockMvc
                .given()
                .param("name", "")
                .get("/custom/lessons");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("$").isEmpty());
    }
}
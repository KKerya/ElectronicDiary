//package com.kirillkabylov.NauJava;
//
//import com.kirillkabylov.NauJava.controller.LessonController;
//import com.kirillkabylov.NauJava.database.LessonRepository;
//import com.kirillkabylov.NauJava.database.SubjectRepository;
//import com.kirillkabylov.NauJava.domain.Group;
//import com.kirillkabylov.NauJava.domain.Lesson;
//import com.kirillkabylov.NauJava.domain.Subject;
//import com.kirillkabylov.NauJava.domain.Teacher;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import io.restassured.module.mockmvc.response.MockMvcResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//public class LessonControllerTest {
//    @Mock
//    private LessonRepository lessonRepository;
//
//    @InjectMocks
//    private LessonController lessonController;
//
//    @BeforeEach
//    void setUp() {
//        RestAssuredMockMvc.standaloneSetup(lessonController);
//    }
//
//    @Test
//    void getLessonsTeacherExists() {
//        Group group = new Group("Group A");
//        Subject subject = new Subject("Math");
//        Subject subject1 = new Subject("Physics");
//        Teacher teacher = new Teacher("teacher", "Name Surname", "pass");
//        teacher.setSubjects(List.of(subject, subject1));
//        Lesson lesson1 = new Lesson(group, subject, teacher, LocalDateTime.now());
//        Lesson lesson2 = new Lesson(group, subject1, teacher, LocalDateTime.now().plusHours(1));
//
//        when(lessonRepository.findLessonByTeacherName("Name Surname"))
//                .thenReturn(List.of(lesson1, lesson2));
//
//        MockMvcResponse response = RestAssuredMockMvc
//                .given()
//                .param("name", "Name Surname")
//                .get("/custom/lessons");
//
//        assertEquals(200, response.getStatusCode());
//        assertEquals(2, response.jsonPath().getList("$").size(), "Должно быть 2 урока");
//    }
//
//    @Test
//    void getLessonsTeacherNotFound() {
//        when(lessonRepository.findLessonByTeacherName("Unknown Teacher"))
//                .thenReturn(List.of());
//
//        MockMvcResponse response = RestAssuredMockMvc
//                .given()
//                .param("name", "Unknown Teacher")
//                .get("/custom/lessons");
//
//        assertEquals(200, response.getStatusCode());
//        assertTrue(response.jsonPath().getList("$").isEmpty(), "Список уроков должен быть пустым");
//    }
//
//    @Test
//    void getLessonsEmptyName() {
//        MockMvcResponse response = RestAssuredMockMvc
//                .given()
//                .param("name", "")
//                .get("/custom/lessons");
//
//        assertEquals(200, response.getStatusCode());
//        assertTrue(response.jsonPath().getList("$").isEmpty());
//    }
//}
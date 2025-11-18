package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.database.*;
import com.kirillkabylov.NauJava.domain.*;
import com.kirillkabylov.NauJava.services.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.dialect.GroupByConstantRenderingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class NauJavaApplicationTests {
}

@SpringBootTest
@Transactional
class TeacherRepositoryImplTest {
    private final TeacherRepositoryCustom teacherRepositoryCustom;
    private final EntityManager entityManager;

    @Autowired
    public TeacherRepositoryImplTest(TeacherRepositoryCustom teacherRepositoryCustom, EntityManager entityManager) {
        this.teacherRepositoryCustom = teacherRepositoryCustom;
        this.entityManager = entityManager;
    }

    /**
     * Нахождение учителя по его полю
     */
    @Test
    void testFindByField() {
        Teacher teacher1 = new Teacher("login1", "Иван Иванов", "pass1", "Math");
        Teacher teacher2 = new Teacher("login2", "Петр Петров", "pass2", "Rus");

        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.flush();

        List<Teacher> result = teacherRepositoryCustom.findByField("fullName", "Иван Иванов");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Иван Иванов", result.get(0).getFullName());

        result = teacherRepositoryCustom.findByField("subject", "Rus");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Петр Петров", result.get(0).getFullName());

        result = teacherRepositoryCustom.findByField("fullName", "Неизвестный");
        Assertions.assertTrue(result.isEmpty());
    }
}

@SpringBootTest
@Transactional
class LessonRepositoryImplTest {
    private final LessonRepositoryCustom lessonRepositoryCustom;
    private final EntityManager entityManager;

    @Autowired
    public LessonRepositoryImplTest(LessonRepositoryCustom lessonRepositoryCustom, EntityManager entityManager) {
        this.lessonRepositoryCustom = lessonRepositoryCustom;
        this.entityManager = entityManager;
    }

    /**
     * Тестрирование нахождения урока у данного учителя
     */
    @Test
    void testFindLessonByTeacherName() {
        Teacher teacher1 = new Teacher("login1", "Иван Иванов", "pass1", "Math");
        Teacher teacher2 = new Teacher("login2", "Петр Петров", "pass2", "Rus");

        entityManager.persist(teacher1);
        entityManager.persist(teacher2);

        Lesson lesson1 = new Lesson("11A", "Math", teacher1, LocalDateTime.now());
        Lesson lesson2 = new Lesson("11B", "Math", teacher1, LocalDateTime.now());
        Lesson lesson3 = new Lesson("11A", "Rus", teacher2, LocalDateTime.now());

        entityManager.persist(lesson1);
        entityManager.persist(lesson2);
        entityManager.persist(lesson3);
        entityManager.flush();

        List<Lesson> lessons = lessonRepositoryCustom.findLessonByTeacherName("Иван Иванов");
        Assertions.assertEquals(2, lessons.size());
        Assertions.assertTrue(lessons.contains(lesson1));
        Assertions.assertTrue(lessons.contains(lesson2));

        lessons = lessonRepositoryCustom.findLessonByTeacherName("Петр Петров");
        Assertions.assertEquals(1, lessons.size());
        Assertions.assertTrue(lessons.contains(lesson3));

        lessons = lessonRepositoryCustom.findLessonByTeacherName("Неизвестный");
        Assertions.assertTrue(lessons.isEmpty());
    }
}

@SpringBootTest
class StudentServiceImplTest {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentServiceImplTest(TeacherRepository teacherRepository,
                                  StudentRepository studentRepository,
                                  GradeRepository gradeRepository,
                                  GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Тестирование удаления студента и его оценок
     */
    @Test
    @Transactional
    void testDeleteStudent() {
        Teacher teacher1 = new Teacher("login2", "Петр Петров Петрович", "123", "Math");
        Group group = new Group("11A", teacher1);
        Student student = new Student("login", "Иван Иванов Иванович", "123123", group);
        Teacher teacher2 = new Teacher("login3", "Алексадр Алексадров Александорович", "123567", "Rus");
        Student student2 = new Student("login", "Петр Иванов Иванович", "123123", group);


        teacherRepository.save(teacher1);
        group.getStudents().add(student);
        groupRepository.save(group);
        teacherRepository.save(teacher2);
        studentRepository.save(student2);

        Grade grade = new Grade(5, student, "Math", teacher1, LocalDateTime.now());
        Grade grade2 = new Grade(4, student, "Rus", teacher2, LocalDateTime.now());

        gradeRepository.save(grade);
        gradeRepository.save(grade2);
    }

    @Test
    void createStudentAndGroup(){
        Teacher teacher = new Teacher("login2", "Петр Петров Петрович", "123", "Math");
        Group group = new Group("11A", teacher);
        Student student = new Student("login", "Иван Иванов Иванович", "123123", group);
        Student student2 = new Student("login2", "Петр Петров Петрович", "123123", group);
        teacherRepository.save(teacher);
        group.getStudents().add(student);
        group.getStudents().add(student2);
        groupRepository.save(group);
    }
}

package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.database.LessonRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Lesson;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class NauJavaApplicationTests {
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	LessonRepository lessonRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void lessonsFindByNameTest(){
		Teacher teacher = new Teacher("login123", "Name Surname", "123123", "Math");
		teacherRepository.save(teacher);
		Teacher teacher1 = new Teacher("login1234", "Name Surname", "123123", "Math");
		teacherRepository.save(teacher1);

		Lesson lesson = new Lesson("11A", "Math", teacher, LocalDateTime.now(), "111");
		lessonRepository.save(lesson);

		Lesson lesson2 = new Lesson("10A", "Rus", teacher, LocalDateTime.now(), "111");
		lessonRepository.save(lesson2);

		System.out.println(lessonRepository.findCoursesByTeacherName(teacher.getFullName()));
		System.out.println(teacherRepository.findByFullNameAndSubject("Name Surname", "Math"));
	}
}

package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NauJavaApplicationTests {
	@Autowired
	StudentRepository studentRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void studentCreate(){
		Student student = new Student("NewStudent", "Name Surname", "123123", "11A");
		studentRepository.saveAndFlush(student);
	}
}

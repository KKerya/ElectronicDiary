package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.Exceptions.ResourceNotFoundException;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("custom/students/page")
public class StudentPageController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/list")
    public String studentListView(Model model) {
        List<Student> students = (List<Student>) studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found");
        }
        List<StudentDto> studentsDto = students.stream()
                .map(s -> new StudentDto(
                        s.getId(),
                        s.getFullName(),
                        s.getGroup().getName()
                ))
                .toList();

        model.addAttribute("students", studentsDto);
        return "studentList.html";
    }
}

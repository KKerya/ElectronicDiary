package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("custom/students/view")
public class StudentControllerView {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/list")
    public String studentListView(Model model) {
        Iterable<Student> students = studentRepository.findAll();
        if (!students.iterator().hasNext()) {
            throw new ResourceNotFoundException("No students found");
        }
        model.addAttribute("students", students);
        return "studentList.html";
    }
}

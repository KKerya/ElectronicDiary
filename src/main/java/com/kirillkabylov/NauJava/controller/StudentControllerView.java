package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.database.GroupRepository;
import com.kirillkabylov.NauJava.database.StudentRepository;
import com.kirillkabylov.NauJava.database.TeacherRepository;
import com.kirillkabylov.NauJava.domain.Group;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("custom/students/view")
public class StudentControllerView {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/list")
    public String studentListView(Model model){
        Iterable<Student> products = studentRepository.findAll();
        model.addAttribute("students", products);
        return "studentList.html";
    }
}

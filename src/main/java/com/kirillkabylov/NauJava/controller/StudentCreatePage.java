package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("student")
public class StudentCreatePage {
    private final GroupService groupService;

    @Autowired
    public StudentCreatePage(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/create")
    public String createStudentPage(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "admin/studentCreate";
    }
}

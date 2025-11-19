package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Security;

@Controller
public class HomeController {
    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLogin = authentication.getName();

        UserEntity user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException(userLogin));

        model.addAttribute("fullName", user.getFullName());
        return "home";
    }
}
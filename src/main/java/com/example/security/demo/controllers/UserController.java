package com.example.security.demo.controllers;

import com.example.security.demo.model.User;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userStartPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found!", principal.getName()));
        }
        model.addAttribute("user", user);
        return "/user";
    }
}

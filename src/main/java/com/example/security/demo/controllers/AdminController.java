package com.example.security.demo.controllers;

import com.example.security.demo.model.User;
import com.example.security.demo.service.RoleService;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private static final String REDIRECT = "redirect:/admin";


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("username", principal.getName());
        model.addAttribute("role", userService.findByUsername(principal.getName()).getRoles());
        return "welcome";
    }

    @GetMapping("/user/{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") Long id, Model roles) {
        roles.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }


    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return REDIRECT;
    }

    @GetMapping("/new")
    public String getNewUserPage(@ModelAttribute("user") User user) {
        userService.save(user);
        return REDIRECT;
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return REDIRECT;
    }

    @DeleteMapping("admin/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return REDIRECT;
    }
}

package com.example.security.demo.controllers;

import com.example.security.demo.model.User;
import com.example.security.demo.service.RoleService;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user";
    }

    @GetMapping(value = "/admin")
    public String getStartPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "welcome";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id, Model roles) {
        roles.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findAll());
        return "new";
    }

    @PostMapping("/user")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

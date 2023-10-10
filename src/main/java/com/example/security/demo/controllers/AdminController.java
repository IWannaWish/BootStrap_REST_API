package com.example.security.demo.controllers;

import com.example.security.demo.model.User;
import com.example.security.demo.service.RoleService;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final static  String START_PAGE = "\"redirect:/admin\"";

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
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









//
////    @GetMapping("user/{id}")
////    public String getUserPageById(@PathVariable("id") Long id, Model model) {
////        model.addAttribute("users", userService.getUserById(id));
////        return "/show";
////    }
//
//    @GetMapping("/users/new")
//    public String createUserPage(Model model) {
//        List<Role> roles = roleService.getAllRoles();
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roles);
//        return "/new";
//    }
//
//    @GetMapping("/users/{id}/edit")
//    public String editUserPage(@PathVariable("id") Long id, Model model) {
//        User user = userService.getUserById(id);
//        List<Role> roles = roleService.getAllRoles();
//
//        model.addAttribute("users", user);
//        model.addAttribute("roles", roles);
//
//        return "/edit";
//    }
//
//    @PatchMapping("/users/{id}")
//    public String updateUser(@Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
//
//        String rawPassword = user.getPassword();
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        user.setPassword(encodedPassword);
//
//        if (bindingResult.hasErrors()) {
//            return "/edit";
//        }
//        userService.update(user);
//        return START_PAGE;
//    }
//
//    @PostMapping("/users")
//    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/new";
//        }
//        String rawPassword = user.getPassword();
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        user.setPassword(encodedPassword);
//
//        userService.save(user);
//
//        return START_PAGE;
//    }
//
//    @DeleteMapping("/users/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return START_PAGE;
//    }

//    @GetMapping("/admin")
//    public String getAdminPage(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return START_PAGE;
//    }
//
//    @GetMapping("admin/users")
//    public String getUserPage(@RequestParam("id") Long id, Model model) {
//        model.addAttribute("users", userService.show(id));
//        return "user/show";
//    }
//
//    @GetMapping("admin/new")
//    public String getNewUserPage(Model model) {
//        model.addAttribute("user", new User());
//        return "admin/new";
//    }
//
//    @PostMapping()
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:localhost8080/admin";
//    }
//
//    @GetMapping("admin/edit")
//    public String getEditUserPage(Model model, @RequestParam("id") Long id) {
//        model.addAttribute("user", userService.show(id));
//        return "admin/edit";
//    }
//
//
//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute("user") User user) {
//        userService.update(user);
//        return "redirect:localhost8080/admin";
//    }
//
//    @GetMapping("/delete")
//    public String deleteUser(@RequestParam(value = "id") Long id) {
//        userService.delete(id);
//        return "redirect:localhost8080/admin";
//    }
}

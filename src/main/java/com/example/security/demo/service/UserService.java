package com.example.security.demo.service;

import com.example.security.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    void save(User user);
    void delete(Long id);
    void update(User user);
}

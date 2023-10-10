package com.example.security.demo.service;

import com.example.security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    void save(Role role);
    void deleteById(Long id);
    Role showRoleById(Long id);
}

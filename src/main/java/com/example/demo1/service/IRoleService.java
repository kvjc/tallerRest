package com.example.demo1.service;

import java.util.List;
import java.util.Optional;

import com.example.demo1.model.Role;

public interface IRoleService {
    Role save(Role role); 
    Optional<Role> findById(Long id); 
    Iterable<Role> findAll();
    void deleteById(Long id);
    Role updateRole(Long id, Role role);
    List<Role> findAvailableRolesForUser(Long id);
    long countRoles();
}

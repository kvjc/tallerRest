package com.example.demo1.service;

import java.util.Optional;

import com.example.demo1.dto.permission.PermissionInDTO;
import com.example.demo1.dto.permission.PermissionUpdateDTO;
import com.example.demo1.model.Permission;

public interface IPermissionService {
    Permission save(PermissionInDTO permissionInDTO); 
    Optional<Permission> findById(Long id); 
    Iterable<Permission> findAll();
    void deleteById(Long id);
    Permission updatePermission(Long id, PermissionUpdateDTO permissionUpdateDTO);
}

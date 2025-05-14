package com.example.demo1.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Permission;
import com.example.demo1.repository.IPermissionRepository;
import com.example.demo1.service.IPermissionService;

@Service
public class PermissionServiceImpl implements IPermissionService {

    private final IPermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Iterable<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission updatePermission(Long id, Permission updatedPermission) {
        Optional<Permission> existingPermission = permissionRepository.findById(id);
        if (existingPermission.isPresent()) {
            Permission permission = existingPermission.get();
            permission.setName(updatedPermission.getName());
            // Actualiza otros campos según sea necesario
            return permissionRepository.save(permission);
        } else {
            throw new RuntimeException("Permiso no encontrado con id: " + id);
        }
    }
}

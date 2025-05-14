package com.example.demo1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.model.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}

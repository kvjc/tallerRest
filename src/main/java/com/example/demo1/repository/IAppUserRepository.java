package com.example.demo1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.model.AppUser;

public interface IAppUserRepository extends JpaRepository<AppUser, Long> {
    @EntityGraph(attributePaths = {
    "roles",
    "roles.permissions"
})
    Optional<AppUser> findByName(String name);

    boolean existsByEmail(String email);
}

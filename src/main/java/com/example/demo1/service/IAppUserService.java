package com.example.demo1.service;

import java.util.Optional;

import com.example.demo1.dto.user.UserInDTO;
import com.example.demo1.dto.user.UserUpdateDTO;
import com.example.demo1.model.AppUser;

public interface IAppUserService {
    AppUser save(UserInDTO userInDTO);
    AppUser findById(Long id);
    Optional<AppUser> findByUsername(String username);
    Iterable<AppUser> findAll();
    void deleteById(Long id);
    AppUser updateUser(Long id, UserUpdateDTO userUpdateDTO);
    long countUsers();
    void assignRoleToUser(Long userId, Long roleId);
    boolean registerUser(AppUser appUser);
    boolean existsByEmail(String email);
    void removeRoleFromUser(Long userId, Long roleId);



}

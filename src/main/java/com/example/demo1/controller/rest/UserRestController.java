package com.example.demo1.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.user.UserInDTO;
import com.example.demo1.dto.user.UserOutDTO;
import com.example.demo1.dto.user.UserUpdateDTO;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.AppUser;
import com.example.demo1.service.IAppUserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final IAppUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserRestController(IAppUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserOutDTO> createUser(@RequestBody UserInDTO userInDTO) {
        AppUser user = userMapper.toAppUser(userInDTO);

        user.setPassword(userInDTO.getPassword());

        AppUser savedUser = userService.save(user);

        UserOutDTO userOutDTO = userMapper.toUserOutDTO(savedUser);
        
        return ResponseEntity.status(201).body(userOutDTO);
    }


    @GetMapping
    public ResponseEntity<List<UserOutDTO>> getAllUsers() {
        Iterable<AppUser> users = userService.findAll();

        List<AppUser> userList = new ArrayList<>();
        users.forEach(userList::add);

        List<UserOutDTO> userOutDTOs = userMapper.toUserOutDTOs(userList);

        return ResponseEntity.ok(userOutDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO userUpdateDTO) {

        Optional<AppUser> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AppUser existingUser = optionalUser.get();

        userMapper.updateAppUserFromDto(userUpdateDTO, existingUser);

        userService.save(existingUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<AppUser> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutDTO> getUserById(@PathVariable Long id) {
        Optional<AppUser> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserOutDTO userOutDTO = userMapper.toUserOutDTO(optionalUser.get());
        return ResponseEntity.ok(userOutDTO);
    }


}
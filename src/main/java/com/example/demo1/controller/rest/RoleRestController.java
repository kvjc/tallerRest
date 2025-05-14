package com.example.demo1.controller.rest;

import com.example.demo1.dto.role.RoleInDTO;
import com.example.demo1.dto.role.RoleOutDTO;
import com.example.demo1.dto.role.RoleUpdateDTO;
import com.example.demo1.mapper.RoleMapper;
import com.example.demo1.model.Role;
import com.example.demo1.service.IRoleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
    
    private final IRoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleRestController(IRoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping
    public ResponseEntity<RoleOutDTO> createRole(@RequestBody RoleInDTO roleInDTO) {
        Role role = roleMapper.toRole(roleInDTO);
        Role savedRole = roleService.save(role);
        RoleOutDTO roleOutDTO = roleMapper.toRoleOutDTO(savedRole);

        return ResponseEntity.status(201).body(roleOutDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoleOutDTO>> getAllRoles() {
        Iterable<Role> roles = roleService.findAll();

        List<Role> roleList = new ArrayList<>();
        roles.forEach(roleList::add);

        List<RoleOutDTO> roleOutDTOs = roleMapper.toRoleOutDTOs(roleList);

        return ResponseEntity.ok(roleOutDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(
            @PathVariable Long id,
            @RequestBody RoleUpdateDTO roleUpdateDTO) {

        Optional<Role> optionalRole = roleService.findById(id);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Role existingRole = optionalRole.get();
        existingRole = roleMapper.updateRoleFromDto(roleUpdateDTO, existingRole);
        roleService.save(existingRole);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        roleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleOutDTO> getRoleById(@PathVariable Long id) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoleOutDTO roleOutDTO = roleMapper.toRoleOutDTO(optionalRole.get());
        return ResponseEntity.ok(roleOutDTO);
    }
}
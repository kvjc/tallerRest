package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;
import com.example.demo1.repository.IAppUserRepository;
import com.example.demo1.repository.IRoleRepository;
import com.example.demo1.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IAppUserRepository userRepository;

    public RoleServiceImpl(IRoleRepository roleRepository, 
                         IAppUserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;}

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setName(updatedRole.getName());
            return roleRepository.save(role);
        } else {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
    }

    @Override
    public List<Role> findAvailableRolesForUser(Long userId) {
        List<Role> todosLosRoles = roleRepository.findAll();
        
        Set<Role> rolesDelUsuario = userRepository.findById(userId).get().getRoles();
        
        List<Role> rolesDisponibles = new ArrayList<>();
        for (Role rol : todosLosRoles) {
            if (!rolesDelUsuario.contains(rol)) {
                rolesDisponibles.add(rol);
            }
        }
    
    return rolesDisponibles;
    }
                
    
    @Override
    public long countRoles() {
        return roleRepository.count();
    }
}

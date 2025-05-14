package com.example.demo1.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;
import com.example.demo1.repository.IAppUserRepository;
import com.example.demo1.repository.IRoleRepository;
import com.example.demo1.service.IAppUserService;

@Service
public class AppUserServiceImpl implements IAppUserService {

    private final IAppUserRepository appUserRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(IAppUserRepository userRepository, 
        IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AppUser save(AppUser appUser) {
    if (appUser.getRoles() == null || appUser.getRoles().isEmpty()) {
        throw new IllegalArgumentException("El usuario debe tener al menos un rol.");
    }
        return appUserRepository.save(appUser);
        }
    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public Iterable<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByName(username); 
    }

    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public AppUser updateUser(Long id, AppUser appUser) {
        Optional<AppUser> existingUserOpt = appUserRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            AppUser existingUser = existingUserOpt.get();
            existingUser.setName(appUser.getName());
            existingUser.setEmail(appUser.getEmail());
            existingUser.setPassword(appUser.getPassword());
            // Actualiza otros campos según sea necesario
            return appUserRepository.save(existingUser);
        } else {
            return null; // o puedes lanzar una excepción si prefieres
        }
    }


    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            appUserRepository.save(user);
        }
    }


    @Override
    public long countUsers() {
        return appUserRepository.count();
    }

    @Override
    public boolean registerUser(AppUser appUser) {
        try {
            // Verificar si el correo ya existe
            if (appUser.getEmail() != null && appUserRepository.existsByEmail(appUser.getEmail())) {
                return false;
            }

            appUser.setId(null);

            // Codificar la contraseña antes de guardarla
            String encodedPassword = passwordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);

            // Inicializar la colección de roles si es null
            if (appUser.getRoles() == null) {
                appUser.setRoles(new HashSet<>());
            }

            // Asignar un rol por defecto
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
            appUser.getRoles().add(defaultRole);

            // Guardar al usuario en la base de datos
            appUserRepository.save(appUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            AppUser user = optionalUser.get();
            Role role = optionalRole.get();

            user.getRoles().remove(role);
            appUserRepository.save(user);
        }
    }




}

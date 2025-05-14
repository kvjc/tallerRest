package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;
import com.example.demo1.repository.IAppUserRepository;
import com.example.demo1.repository.IRoleRepository;
import com.example.demo1.service.impl.AppUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppUserServiceImplTest {

    private IAppUserRepository appUserRepository;
    private AppUserServiceImpl appUserService;

    @BeforeEach
    void setUp() {
        appUserRepository = mock(IAppUserRepository.class);
        IRoleRepository roleRepository = mock(IRoleRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        appUserService = new AppUserServiceImpl(appUserRepository, roleRepository, passwordEncoder);
    }

    // Test 1: Guardar usuario sin roles → lanza excepción
    @Test
    void saveAppUser_WithoutRoles_ThrowsException() {
        // Creamos un usuario sin roles
        AppUser user = new AppUser();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        // Aseguramos que el conjunto de roles esté vacío (o nulo)
        user.setRoles(null);

        // Se espera que el servicio lance una excepción si el usuario no tiene roles.
        // IMPORTANTE: En AppUserServiceImpl.save(), debe agregarse la validación:
        // if (appUser.getRoles() == null || appUser.getRoles().isEmpty()) { throw new IllegalArgumentException("El usuario debe tener al menos un rol."); }
        assertThrows(IllegalArgumentException.class, () -> appUserService.save(user));
        // Verificamos que el repositorio nunca se invoque para guardar.
        verify(appUserRepository, never()).save(any());
    }

    // Test 2: Guardar usuario con roles → retorna usuario guardado
    @Test
    void saveAppUser_WithValidRoles_ReturnsSavedUser() {
        // Creamos un usuario con un rol
        AppUser user = new AppUser();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        // Aseguramos que el set de roles esté inicializado y se agregue el rol
        user.setRoles(Set.of(role));

        // Simulamos que el repositorio asigna un ID al usuario guardado
        AppUser savedUser = new AppUser();
        savedUser.setId(1L);
        savedUser.setName(user.getName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());
        savedUser.setRoles(user.getRoles());

        when(appUserRepository.save(user)).thenReturn(savedUser);

        AppUser result = appUserService.save(user);
        assertNotNull(result, "El usuario guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del usuario no coincide");
        assertEquals(user.getName(), result.getName(), "El nombre del usuario no coincide");

        verify(appUserRepository, times(1)).save(user);
    }

    // Test 3: Buscar usuario por ID existente → retorna usuario
    @Test
    void findById_ExistingUser_ReturnsUser() {
        Long userId = 1L;
        AppUser user = new AppUser();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        user.setRoles(Set.of(role));

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<AppUser> result = appUserService.findById(userId);
        assertTrue(result.isPresent(), "El usuario no fue encontrado");
        assertEquals(userId, result.get().getId(), "El ID del usuario no coincide");
    }

    // Test 4: Buscar usuario por ID inexistente → retorna vacío
    @Test
    void findById_NonExistentUser_ReturnsEmpty() {
        Long userId = 999L;
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<AppUser> result = appUserService.findById(userId);
        assertFalse(result.isPresent(), "Se esperaba que no se encontrara ningún usuario");
    }

    // Test 5: Eliminar usuario con ID válido → elimina correctamente
    @Test
    void deleteUser_ValidId_DeletesUser() {
        Long userId = 1L;
        // Configuramos el repositorio para que al eliminar no lance excepciones
        doNothing().when(appUserRepository).deleteById(userId);

        appUserService.deleteById(userId);

        verify(appUserRepository, times(1)).deleteById(userId);
    }
}

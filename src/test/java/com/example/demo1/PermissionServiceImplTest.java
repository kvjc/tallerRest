package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;

import com.example.demo1.model.Permission;
import com.example.demo1.repository.IPermissionRepository;
import com.example.demo1.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PermissionServiceImplTest {

    private IPermissionRepository permissionRepository;
    private PermissionServiceImpl permissionService;

    @BeforeEach
    void setup() {
        permissionRepository = mock(IPermissionRepository.class);
        permissionService = new PermissionServiceImpl(permissionRepository);
    }

    // Test 1: Guardar permiso → retorna permiso guardado
    @Test
    void savePermission_ReturnsSavedPermission() {
        Permission permission = new Permission();
        permission.setName("READ");

        Permission savedPermission = new Permission();
        savedPermission.setId(1L);
        savedPermission.setName(permission.getName());
        savedPermission.setRoles(new HashSet<>()); // Aseguramos que roles esté inicializado

        when(permissionRepository.save(permission)).thenReturn(savedPermission);

        Permission result = permissionService.save(permission);
        assertNotNull(result, "El permiso guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del permiso no coincide");
        assertEquals("READ", result.getName(), "El nombre del permiso no coincide");

        verify(permissionRepository, times(1)).save(permission);
    }

    // Test 2: Buscar permiso por ID existente → retorna permiso
    @Test
    void findById_ExistingPermission_ReturnsPermission() {
        Long permissionId = 1L;
        Permission permission = new Permission();
        permission.setId(permissionId);
        permission.setName("READ");
        permission.setRoles(new HashSet<>());

        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(permission));

        Optional<Permission> result = permissionService.findById(permissionId);
        assertTrue(result.isPresent(), "El permiso no fue encontrado");
        assertEquals(permissionId, result.get().getId(), "El ID del permiso no coincide");
    }

    // Test 3: Consultar todos los permisos → retorna lista de permisos
    @Test
    void findAll_ReturnsListOfPermissions() {
        Permission p1 = new Permission(1L, "READ", new HashSet<>());
        Permission p2 = new Permission(2L, "WRITE", new HashSet<>());

        when(permissionRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Permission> permissions = (List<Permission>) permissionService.findAll();
        assertEquals(2, permissions.size(), "El tamaño de la lista de permisos no coincide");
    }

    // Test 4: Actualizar permiso existente → retorna permiso actualizado
    @Test
    void updatePermission_ExistingPermission_UpdatesPermission() {
        Long permissionId = 1L;
        Permission existingPermission = new Permission();
        existingPermission.setId(permissionId);
        existingPermission.setName("READ");
        existingPermission.setRoles(new HashSet<>());

        Permission updateInfo = new Permission();
        updateInfo.setName("READ/WRITE");

        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(existingPermission));

        Permission updatedPermission = new Permission();
        updatedPermission.setId(permissionId);
        updatedPermission.setName(updateInfo.getName());
        updatedPermission.setRoles(existingPermission.getRoles());

        when(permissionRepository.save(existingPermission)).thenReturn(updatedPermission);

        Permission result = permissionService.updatePermission(permissionId, updateInfo);
        assertEquals("READ/WRITE", result.getName(), "El nombre no se actualizó correctamente");
    }

    // Test 5: Eliminar permiso con ID válido → elimina correctamente
    @Test
    void deletePermission_ValidId_DeletesPermission() {
        Long permissionId = 1L;
        doNothing().when(permissionRepository).deleteById(permissionId);

        permissionService.deleteById(permissionId);
        verify(permissionRepository, times(1)).deleteById(permissionId);
    }
}

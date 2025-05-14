package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;

import com.example.demo1.model.Role;
import com.example.demo1.repository.IAppUserRepository;
import com.example.demo1.repository.IRoleRepository;
import com.example.demo1.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleServiceImplTest {

    private IRoleRepository roleRepository;
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        roleRepository = mock(IRoleRepository.class);
        IAppUserRepository appUserRepository = mock(IAppUserRepository.class);
        
        roleService = new RoleServiceImpl(roleRepository, appUserRepository);
    }


    // Test 1: Guardar rol → retorna rol guardado
    @Test
    void saveRole_ReturnsSavedRole() {
        Role role = new Role();
        role.setName("ADMIN");
        // No es necesario setear roles (ya que Role tiene "permissions" y "appUsers")

        Role savedRole = new Role();
        savedRole.setId(1L);
        savedRole.setName("ADMIN");
        // Inicializamos los conjuntos vacíos para appUsers y permissions si es necesario
        savedRole.setAppUsers(new HashSet<>());
        savedRole.setPermissions(new HashSet<>());

        when(roleRepository.save(role)).thenReturn(savedRole);

        Role result = roleService.save(role);
        assertNotNull(result, "El rol guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del rol no coincide");
        assertEquals("ADMIN", result.getName(), "El nombre del rol no coincide");

        verify(roleRepository, times(1)).save(role);
    }

    // Test 2: Buscar rol por ID existente → retorna rol
    @Test
    void findById_ExistingRole_ReturnsRole() {
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        role.setName("ADMIN");
        role.setAppUsers(new HashSet<>());
        role.setPermissions(new HashSet<>());

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findById(roleId);
        assertTrue(result.isPresent(), "El rol no fue encontrado");
        assertEquals(roleId, result.get().getId(), "El ID del rol no coincide");
    }

    // Test 3: Buscar rol por ID inexistente → retorna vacío
    @Test
    void findById_NonExistentRole_ReturnsEmpty() {
        Long roleId = 999L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findById(roleId);
        assertFalse(result.isPresent(), "Se esperaba que no se encontrara ningún rol");
    }

    // Test 4: Consultar todos los roles → retorna lista de roles
    @Test
    void findAll_ReturnsListOfRoles() {
        Role r1 = new Role();
        r1.setId(1L);
        r1.setName("ADMIN");
        r1.setAppUsers(new HashSet<>());
        r1.setPermissions(new HashSet<>());
        
        Role r2 = new Role();
        r2.setId(2L);
        r2.setName("USER");
        r2.setAppUsers(new HashSet<>());
        r2.setPermissions(new HashSet<>());

        when(roleRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Role> roles = (List<Role>) roleService.findAll();
        assertEquals(2, roles.size(), "El tamaño de la lista de roles no coincide");
    }

    // Test 5: Actualizar rol existente → retorna rol actualizado
    @Test
    void updateRole_ExistingRole_UpdatesRole() {
        Long roleId = 1L;
        Role existingRole = new Role();
        existingRole.setId(roleId);
        existingRole.setName("ADMIN");
        existingRole.setAppUsers(new HashSet<>());
        existingRole.setPermissions(new HashSet<>());

        Role updateInfo = new Role();
        updateInfo.setName("SUPERADMIN");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));

        Role updatedRole = new Role();
        updatedRole.setId(roleId);
        updatedRole.setName(updateInfo.getName());
        updatedRole.setAppUsers(existingRole.getAppUsers());
        updatedRole.setPermissions(existingRole.getPermissions());

        when(roleRepository.save(existingRole)).thenReturn(updatedRole);

        Role result = roleService.updateRole(roleId, updateInfo);
        assertEquals("SUPERADMIN", result.getName(), "El nombre del rol no se actualizó correctamente");
    }

    // Test 6: Eliminar rol con ID válido → elimina correctamente
    @Test
    void deleteRole_ValidId_DeletesRole() {
        Long roleId = 1L;
        doNothing().when(roleRepository).deleteById(roleId);

        roleService.deleteById(roleId);
        verify(roleRepository, times(1)).deleteById(roleId);
    }
}

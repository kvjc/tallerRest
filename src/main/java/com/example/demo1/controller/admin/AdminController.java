package com.example.demo1.controller.admin;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;
import com.example.demo1.service.IAppUserService;
import com.example.demo1.service.IRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Seguridad aplicada a nivel de clase
public class AdminController {

    private final IAppUserService userService;
    private final IRoleService roleService;

    public AdminController(IAppUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // 1. Panel de control
    @GetMapping("/home")
    public String adminHome(Model model, Authentication auth) {
        // Datos del usuario autenticado
        model.addAttribute("username", auth.getName());
        model.addAttribute("roles", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", ")));
        
        // Estadísticas
        model.addAttribute("totalUsers", userService.countUsers());
        model.addAttribute("totalRoles", roleService.countRoles());
        
        return "admin/home";
    }

    // 2. Gestión de Usuarios
    @GetMapping("/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    // 3. Gestión de Roles
    @GetMapping("/roles")
    public String roleManagement(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin/roles/list";
    }

    // 4. Asignación de Roles
    @GetMapping("/users/{id}/roles")
    public String assignRolesForm(@PathVariable Long id, Model model) {
        AppUser user = userService.findById(id).orElseThrow();
        List<Role> availableRoles = roleService.findAvailableRolesForUser(id);
        
        model.addAttribute("user", user);
        model.addAttribute("availableRoles", availableRoles);
        return "admin/users/assign-roles";
    }

    @PostMapping("/users/{userId}/roles/{roleId}")
    public String assignRoleToUser(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        userService.assignRoleToUser(userId, roleId);
        return "redirect:/admin/users/" + userId + "/roles";
    }
}
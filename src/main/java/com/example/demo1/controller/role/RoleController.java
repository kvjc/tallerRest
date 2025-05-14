package com.example.demo1.controller.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo1.model.Role;
import com.example.demo1.repository.IRoleRepository;

@Controller
public class RoleController {

    @Autowired
    private IRoleRepository roleRepository;

    @GetMapping("/roles/create")
    public String showCreateRoleForm() {
        return "user/create"; // Vista para crear roles
    }

    @PostMapping("/roles/create")
    public String createRole(@RequestParam String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{roleId}/delete")
    public String deleteRole(@PathVariable Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        

        if (!role.getAppUsers().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el rol porque tiene usuarios asignados.");
        }

        roleRepository.deleteById(roleId);
        return "redirect:/roles";
    }

}

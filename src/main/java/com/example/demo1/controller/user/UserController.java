package com.example.demo1.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;
import com.example.demo1.service.IAppUserService;
import com.example.demo1.service.IRoleService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final IAppUserService userService;
    private final IRoleService roleService;

    @Autowired
    public UserController(IAppUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Vista para el registro de usuario
    /*@GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "user/register";
    }*/

    // Manejo del formulario de registro de usuario
    /*@PostMapping("/register")
    public String registerUser(@ModelAttribute("user") AppUser user, Model model) {
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "user/register";
        }
        userService.save(user);
        return "redirect:/user/login";
    }*/

    // Vista para iniciar sesión
    /*@GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }*/

    // Vista para listar usuarios
    @GetMapping("/list")
    public String listUsers(Model model) {
        Iterable<AppUser> iterable = userService.findAll();
        List<AppUser> users = new ArrayList<>();
        iterable.forEach(users::add); // Convierte Iterable a List
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/roles/create")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "user/createRole";
    }

    // Asociar roles a usuarios
    @GetMapping("/assign-role")
    public String assignRoleForm(Model model) {
        Iterable<AppUser> users = userService.findAll();
        Iterable<Role> roles = roleService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "user/assignRole";
    }
    

    @PostMapping("/assignRole")
    public String assignRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        userService.assignRoleToUser(userId, roleId);
        return "redirect:/user/list";
    }

    // Eliminar rol de un usuario
    @PostMapping("/removeRole")
    public String removeRoleFromUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        userService.removeRoleFromUser(userId, roleId);
        return "redirect:/user/list";
    }

    @PostMapping("/roles/save")
    public String saveRole(@ModelAttribute Role role) {
        roleService.save(role);
        return "redirect:/user/list";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        long userCount = StreamSupport.stream(userService.findAll().spliterator(), false).count();

        long roleCount = StreamSupport.stream(roleService.findAll().spliterator(), false).count();

        model.addAttribute("userCount", userCount);
        model.addAttribute("roleCount", roleCount);
        
        return "user/dashboard";
    }

    




}

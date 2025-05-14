package com.example.demo1.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class UserOutDTO {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

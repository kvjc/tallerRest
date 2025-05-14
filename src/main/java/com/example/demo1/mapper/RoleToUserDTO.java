package com.example.demo1.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class RoleToUserDTO {
    
    private Long userId;
    private String roleName;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
}

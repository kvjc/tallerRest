package com.example.demo1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo1.dto.role.RoleInDTO;
import com.example.demo1.dto.role.RoleOutDTO;
import com.example.demo1.dto.role.RoleUpdateDTO;
import com.example.demo1.model.Role;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleOutDTO toRoleOutDTO(Role role);

    List<RoleOutDTO> toRoleOutDTOs(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appUsers", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleInDTO roleInDTO);

    @Mapping(target = "name", source = "roleUpdateDTO.name")
    Role updateRoleFromDto(RoleUpdateDTO roleUpdateDTO, Role role);

    
}

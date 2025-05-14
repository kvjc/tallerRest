package com.example.demo1.mapper;

import java.util.List;

import com.example.demo1.dto.permission.PermissionInDTO;
import com.example.demo1.dto.permission.PermissionOutDTO;
import com.example.demo1.dto.permission.PermissionUpdateDTO;
import com.example.demo1.model.Permission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionOutDTO toPermissionOutDTO(Permission permission);

    List<PermissionOutDTO> toPermissionOutDTOs(List<Permission> permissions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Permission toPermission(PermissionInDTO dto);

}

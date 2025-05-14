package com.example.demo1.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import org.mapstruct.Named;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo1.dto.user.UserInDTO;
import com.example.demo1.dto.user.UserOutDTO;
import com.example.demo1.dto.user.UserUpdateDTO;
import com.example.demo1.model.AppUser;
import com.example.demo1.model.Role;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStringList")
    UserOutDTO toUserOutDTO(AppUser user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStringList")
    List<UserOutDTO> toUserOutDTOs(List<AppUser> users);

    @Mapping(target = "name", source = "username")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    AppUser toAppUser(UserInDTO userInDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateAppUserFromDto(UserUpdateDTO userUpdateDTO, @MappingTarget AppUser appUser);

    @Named("mapRolesToStringList")
    default List<String> mapRolesToStringList(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
}}

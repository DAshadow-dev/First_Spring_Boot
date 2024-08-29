package com.example.spring2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.spring2.dto.request.RoleRequest;
import com.example.spring2.dto.response.RoleResponse;
import com.example.spring2.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}

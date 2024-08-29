package com.example.spring2.mapper;

import org.mapstruct.Mapper;
import com.example.spring2.dto.request.PermissionRequest;
import com.example.spring2.dto.response.PermissionResponse;
import com.example.spring2.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}

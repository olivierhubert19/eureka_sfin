package com.example.authen_service.service;

import com.example.authen_service.model.Permission;
import com.example.authen_service.model.Role;
import com.example.authen_service.repository.RoleHasPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleHasPermissionService {
    @Autowired
    private RoleHasPermissionRepository roleHasPermissionRepository;

    public boolean existsRoleHasPermissionByPermissionAndRole(Permission permission, Role role){
        return roleHasPermissionRepository.existsRoleHasPermissionByPermissionAndRole(permission,role);
    }
}

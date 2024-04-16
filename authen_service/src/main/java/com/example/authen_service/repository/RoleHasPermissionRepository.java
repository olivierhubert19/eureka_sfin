package com.example.authen_service.repository;

import com.example.authen_service.model.Permission;
import com.example.authen_service.model.Role;
import com.example.authen_service.model.RoleHasPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleHasPermissionRepository extends JpaRepository<RoleHasPermission,String> {
    boolean existsRoleHasPermissionByPermissionAndRole(Permission permission, Role role);
}

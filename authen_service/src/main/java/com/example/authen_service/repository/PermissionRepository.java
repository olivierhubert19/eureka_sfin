package com.example.authen_service.repository;

import com.example.authen_service.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
    Permission findFirstByPathAndMethod(String path,String method);
}

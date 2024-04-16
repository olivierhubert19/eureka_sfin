package com.example.authen_service.service;

import com.example.authen_service.model.Permission;
import com.example.authen_service.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission getPermissionByPathAndMethod(String path, String method){
        return permissionRepository.findFirstByPathAndMethod(path,method);
    }

}

package com.example.authen_service.service;

import com.example.authen_service.model.Permission;
import com.example.authen_service.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission getPermissionByPathAndMethod(String path, String method){
        return permissionRepository.findFirstByPathAndMethod(path,method);
    }

    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }

    public Permission findById(String id){
        try {
            return permissionRepository.findById(id).get();
        }catch (Exception e){
            return null;
        }
    }

    public boolean save(Permission permission){
       try {
           permissionRepository.save(permission);
           return true;
       }catch (Exception e){
           return false;
       }
    }
}

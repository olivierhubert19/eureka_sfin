package com.example.authen_service.controller;

import com.example.authen_service.model.Permission;
import com.example.authen_service.model.Role;
import com.example.authen_service.service.PermissionService;
import com.example.authen_service.service.RoleHasPermissionService;
import com.example.authen_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authen")
@RequiredArgsConstructor
public class AuthenController {
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final RoleHasPermissionService roleHasPermissionService;

    @PostMapping ("/check")
    public ResponseEntity<?> Authen(@RequestBody MultiValueMap<String, String> params){
        String path = params.getFirst("path");
        String method = params.getFirst("method");
        String code = params.getFirst("role");
        Permission permission = permissionService.getPermissionByPathAndMethod(path,method);
        if(permission==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find this URL");
        if(!permission.isActive()||!permission.isPublic()) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Can't access this URL");
        Role role = roleService.findByCode(code);
        if (role==null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can't find role");
        boolean roleHasPermissionExist = roleHasPermissionService.existsRoleHasPermissionByPermissionAndRole(permission,role);
//        [{role_id, permission_id, path, method, isPermission}]
        final String responseString = role.getId()+", "+permission.getId()+", "+path+", "+method+", isPermission:"+roleHasPermissionExist;
        if(roleHasPermissionExist) ResponseEntity.ok(responseString);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not have access");
    }

}

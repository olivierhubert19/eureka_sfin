package com.example.authen_service.controller;

import com.example.authen_service.lib.UUID;
import com.example.authen_service.model.Permission;
import com.example.authen_service.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping("/get_all")
    public ResponseEntity<?> getAllPermission(){
        List<Permission> permissionList = permissionService.getAll();
        if(permissionList.isEmpty()) return ResponseEntity.ok("Does not exist permission");
        return ResponseEntity.ok(permissionList);
    }

    @PutMapping("/public/{id}")
    public ResponseEntity<?> publicPermission(@PathVariable("id") String id){
        Permission permission = permissionService.findById(id);
        if(permission==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find");
        else{
            permission.setPublic(true);
            permissionService.save(permission);
            return ResponseEntity.ok("update success");
        }
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<?> activePermission(@PathVariable("id") String id){
            Permission permission = permissionService.findById(id);
            if(permission==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find");
            else{
                permission.setActive(true);
                permissionService.save(permission);
                return ResponseEntity.ok("update success");
            }
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Permission permission) {
        try{
            permission.setId(UUID.genID());
            permissionService.save(permission);
            return ResponseEntity.ok("Save success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Can't save the permission");
        }

    }

}

package com.example.authen_service.controller;

import com.example.authen_service.lib.UUID;
import com.example.authen_service.model.Permission;
import com.example.authen_service.model.Role;
import com.example.authen_service.service.PermissionService;
import com.example.authen_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/get_all")
    public ResponseEntity<?> getAllRole(){
        List<Role> roleList = roleService.getAll();
        if(roleList.isEmpty()) return ResponseEntity.ok("Does not exist permission");
        return ResponseEntity.ok(roleList);
    }

    @PutMapping("/public/{id}")
    public ResponseEntity<?> publicRole(@PathVariable("code") String code){
        Role role = roleService.findByCode(code);
        if(role==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find");
        else{
            role.setActive(true);
            roleService.save(role);
            return ResponseEntity.ok("update success");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Role role) {
        try{
            role.setId(UUID.genID());
            roleService.save(role);
            return ResponseEntity.ok("Save success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Can't save the role");
        }

    }

}

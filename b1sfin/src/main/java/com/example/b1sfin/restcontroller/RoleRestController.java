package com.example.b1sfin.restcontroller;

import com.example.b1sfin.lib.UUID;
import com.example.b1sfin.model.Role;
import com.example.b1sfin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/b1sfin/api/role")
public class RoleRestController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll(){
        List<Role> list = roleService.findAll();
        if(list.isEmpty()){
            Role.Message message = new Role.Message("No data in database");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        else {
            return ResponseEntity.ok(list);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Role role){
        if(role.checkSave()){
            String idRole = UUID.generateUUID();
            role.setId(idRole);
            if(roleService.save(role)){
                Role.Message message = new Role.Message("Save success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Save failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Role is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Role role){
        if(role.checkUpdate()){
            if(roleService.update(role)){
                Role.Message message = new Role.Message("Update success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Update failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Role is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Role role = new Role();
        role.setId(id);
        if(roleService.delete(role)){
            Role.Message message = new Role.Message("Delete success!");
            return ResponseEntity.ok(message);
        }else {
            Role.Message message = new Role.Message("Delete failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
}

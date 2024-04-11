package com.example.b1sfin.restcontroller;

import com.example.b1sfin.lib.UUID;
import com.example.b1sfin.model.Employee;
import com.example.b1sfin.model.Role;
import com.example.b1sfin.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/b1sfin/api/redis",produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@RequiredArgsConstructor
public class RedisRestController {
    private final EmployeeService employeeService;
    @PostMapping("/save")
    public ResponseEntity<?> setEmployeeRedis(@RequestBody Employee employee){
        if(employee.checkSave()){
            String idAgency = UUID.generateUUID();
            employee.setId(idAgency);
            if(employeeService.saveRedis(employee)){
                Role.Message message = new Role.Message("Save success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Save failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Employee is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
    @PostMapping("/employee/update")
    public ResponseEntity<?> updateEmployeeRedis(@RequestBody Employee employee){
        if(employee.checkUpdate()){
            if(employeeService.updateRedis(employee)){
                Role.Message message = new Role.Message("Save success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Update failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Employee is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable("id") String id) {
        Employee employeeCheck = employeeService.getRedis(id);
        if(employeeCheck==null){
            Role.Message message = new Role.Message("Employee don't exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(employeeCheck);
        }
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if(employeeService.deleteRedis(id)){
            Role.Message message = new Role.Message("Delete in redis success");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }else {
            Role.Message message = new Role.Message("Delete in redis failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

}

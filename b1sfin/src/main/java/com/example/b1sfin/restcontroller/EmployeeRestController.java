package com.example.b1sfin.restcontroller;

import com.example.b1sfin.lib.UUID;
import com.example.b1sfin.model.Agency;
import com.example.b1sfin.model.Employee;
import com.example.b1sfin.model.Role;
import com.example.b1sfin.service.AgencyService;
import com.example.b1sfin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/b1sfin/api/employee",produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRestController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AgencyService agencyService;

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody Employee employee) {
        List<Employee> list = employeeService.findByCriteria(employee);
        if (list.isEmpty()) {
            return ResponseEntity.ok(new Role.Message("No employee found with similar information."));
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/find_by_retired/{retired}")
    public ResponseEntity<?> findByRetired(@PathVariable boolean retired){
        List<Employee> list = employeeService.getByRetired(retired);
        if(list.isEmpty()){
            String status;
            if(retired) status = "No one has retired";
            else status = "No one is working";
            Role.Message message = new Role.Message(status);
            return ResponseEntity.ok(message);
        }else {
            return ResponseEntity.ok(list);
        }
    }
    @GetMapping("/find_by_agency/{agencyId}")
    public ResponseEntity<?> findByAgency(@PathVariable String agencyId){

        Agency agency = agencyService.findById(agencyId);
        if(agency==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Agency does not exist");
        }
        List<Employee> list = employeeService.getByAgency(agency);
        if(list.isEmpty()){
            Role.Message message = new Role.Message("No one working in "+ agency.getName());
            return ResponseEntity.ok(message);
        }else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll(){
        List<Employee> list = employeeService.findAll();
        if(list.isEmpty()){
            Role.Message message = new Role.Message("No data in database");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else {
            return ResponseEntity.ok(list);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Employee employee){
        if(employee.checkSave()){
            String idAgency = UUID.generateUUID();
            employee.setId(idAgency);
            if(employeeService.save(employee)){
                Role.Message message = new Role.Message("Save success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Save failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Agency is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Employee employee){
        if(employee.checkUpdate()){
            if(employeeService.update(employee)){
                Role.Message message = new Role.Message("Update success!");
                return ResponseEntity.ok(message);
            }else{
                Role.Message message = new Role.Message("Update failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }
        }else {
            Role.Message message = new Role.Message("Agency is missed data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Employee employee = new Employee();
        employee.setId(id);
        if(employeeService.delete(employee)){
            Role.Message message = new Role.Message("Delete success!");
            return ResponseEntity.ok(message);
        }else {
            Role.Message message = new Role.Message("Delete failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

}

package com.example.b1sfin.restcontroller;

import com.example.b1sfin.lib.UUID;
import com.example.b1sfin.model.Agency;
import com.example.b1sfin.model.Role;
import com.example.b1sfin.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/b1sfin/api/agency")
public class AgencyRestController {
    @Autowired
    private AgencyService agencyService;

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll(){
        List<Agency> list = agencyService.findAll();
        if(list.isEmpty()){
            Role.Message message = new Role.Message("No data in database");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        else {
            return ResponseEntity.ok(list);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Agency agency){
        if(agency.checkSave()){
            String idAgency = UUID.generateUUID();
            agency.setId(idAgency);
            if(agencyService.save(agency)){
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
    public ResponseEntity<?> update(@RequestBody Agency agency){
        if(agency.checkUpdate()){
            if(agencyService.update(agency)){
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
        Agency agency = new Agency();
        agency.setId(id);
        if(agencyService.delete(agency)){
            Role.Message message = new Role.Message("Delete success!");
            return ResponseEntity.ok(message);
        }else {
            Role.Message message = new Role.Message("Delete failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

}

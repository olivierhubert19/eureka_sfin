package com.example.authen_service.service;

import com.example.authen_service.model.Permission;
import com.example.authen_service.model.Role;
import com.example.authen_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role findByCode(String code){
      return roleRepository.findFirstByCode(code);
    }



    public boolean save(Role role){
        try{
            roleRepository.save(role);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

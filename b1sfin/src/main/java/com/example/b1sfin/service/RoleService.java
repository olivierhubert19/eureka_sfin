package com.example.b1sfin.service;

import com.example.b1sfin.model.Agency;
import com.example.b1sfin.model.Role;
import com.example.b1sfin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public boolean save(Role role){
        try{
            if(roleRepository.existsById(role.getId())){
                return false;
            }else{
                roleRepository.save(role);
                return true;
            }

        }catch (Exception e){
            return false;
        }
    }

    public boolean delete(Role role){
        try{
            if(roleRepository.existsById(role.getId())){
                roleRepository.delete(role);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean update(Role role){
        try{
            if(roleRepository.existsById(role.getId())){
                roleRepository.save(role);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}

package com.example.authen_service.service;

import com.example.authen_service.model.Role;
import com.example.authen_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role findByCode(String code){
      return roleRepository.findFirstByCode(code);
    }
}

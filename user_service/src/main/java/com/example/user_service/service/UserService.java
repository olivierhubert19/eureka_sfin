package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return  userRepository.findFirstByUsername(username);
    }

    public boolean save(User user){
        try{
            if(findByUsername(user.getUsername())!=null){
                return false;
            }
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}

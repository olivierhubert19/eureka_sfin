package com.example.user_service.controller;

import com.example.user_service.lib.UUID;
import com.example.user_service.model.User;
import com.example.user_service.service.JwtService;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_service/api")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        if(!user.CheckLogin()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Missed information");
        }
        User userCheck = userService.findByUsername(user.getUsername());
        if(userCheck==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username not exist");
        }
        if(!userCheck.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Wrong password");
        }
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(!user.CheckRegister()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Missed information");
        }
        User userCheck = userService.findByUsername(user.getUsername());
        if(userCheck!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is exist");
        }
        user.setId(UUID.generateID());
        if(userService.save(user)){
            return ResponseEntity.ok("Register success");
        }
            return ResponseEntity.badRequest().body("Cannot register");
    }
}

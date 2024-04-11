package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.service.JwtService;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_service/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        if(!user.Check()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Missed information");
        }
        User userCheck = userService.findByUsername(user.getUsername());
        if(userCheck==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username not exist");
        }
        if(!userCheck.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Wrong password");
        }
        String token = jwtService.generateToken();
        return ResponseEntity.ok(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(!user.Check1()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Missed information");
        }
        User userCheck = userService.findByUsername(user.getUsername());
        if(userCheck!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is exist");
        }
        if(userService.save(user)){
            return ResponseEntity.ok("Register success");
        }
            return ResponseEntity.badRequest().body("Cannot register");
    }
}

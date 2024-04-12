package com.example.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class User {
    @Id
    @NonNull
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String password;


    public boolean CheckLogin() {
        if(username==null){
            return false;
        }
        if (username.isEmpty()){
            return false;
        }
        if(password==null){
            return false;
        }
        return !password.isEmpty();
    }

    public boolean CheckRegister() {
        if(id==null){
            return false;
        }
        if (id.isEmpty()){
            return false;
        }
        if(username==null){
            return false;
        }
        if (username.isEmpty()){
            return false;
        }
        if(password==null){
            return false;
        }
        return !password.isEmpty();
    }
}

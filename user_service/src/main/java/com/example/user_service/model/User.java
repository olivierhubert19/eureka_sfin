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

    public boolean Check() {
        if(username==null){
            return false;
        }
        if (username.isEmpty()){
            return false;
        }
        if(password==null){
            return false;
        }
        if (password.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean Check1() {
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
        if (password.isEmpty()){
            return false;
        }
        return true;
    }
}

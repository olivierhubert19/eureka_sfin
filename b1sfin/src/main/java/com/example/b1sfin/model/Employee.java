package com.example.b1sfin.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Date beginAt;
    private boolean retired;
    @OneToOne
    @JoinColumn(name = "id_agency")
    private Agency agency;
    @OneToOne
    @JoinColumn(name = "id_role")
    private Role role;

    public boolean checkSave() {
        if(name==null) return false;
        if(name.isEmpty()) return false;
        if(phone==null) return false;
        if(phone.isEmpty()) return false;
        if(email==null) return false;
        if(email.isEmpty()) return false;
        if(address==null) return false;
        if(address.isEmpty()) return false;
        if(beginAt==null) return false;
        if(agency==null) return false;
        if(role==null) return false;
        return true;
    }

    public boolean checkUpdate() {
        if(id==null) return false;
        if(id.isEmpty()) return false;
        if(name==null) return false;
        if(name.isEmpty()) return false;
        if(phone==null) return false;
        if(phone.isEmpty()) return false;
        if(email==null) return false;
        if(email.isEmpty()) return false;
        if(address==null) return false;
        if(address.isEmpty()) return false;
        if(beginAt==null) return false;
        if(agency==null) return false;
        if(role==null) return false;
        return true;
    }
}

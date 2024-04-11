package com.example.b1sfin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agency")
public class Agency {
    @Id
    private String id;
    private String name;
    private String status;

    public boolean checkSave() {
        if(this.name==null) return false;
        if(this.name.isEmpty()) return false;
        if(this.status==null) return false;
        if(this.status.isEmpty()) return false;
        return true;
    }

    public boolean checkUpdate() {
        if(this.id==null) return false;
        if(this.id.isEmpty()) return false;
        if(this.name==null) return false;
        if(this.name.isEmpty()) return false;
        if(this.status==null) return false;
        if(this.status.isEmpty()) return false;
        return true;
    }
}

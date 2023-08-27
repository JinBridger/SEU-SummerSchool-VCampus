package app.vcampus.server.entity;

import app.vcampus.server.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "card_number")
    public Integer cardNum;

    @Column(nullable = false) 
    public String password;

    @Column(nullable = false) 
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) 
    public Gender gender;

    @Column(nullable = false) 
    public String phone;
    @Column(nullable = false) 
    public String email;

    @Column(name = "role")
    public String roleStr;

    public String[] getRoles() {
        return roleStr.split(",");
    }

    public void setRoles(String[] roles) {
        this.roleStr = String.join(",", roles);
    }
}

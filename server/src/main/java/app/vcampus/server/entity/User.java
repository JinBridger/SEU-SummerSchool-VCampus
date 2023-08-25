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

    public String password;

    public String name;

    @Enumerated(EnumType.STRING)
    public Gender gender;

    public String phone;
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

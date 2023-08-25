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
    private Integer cardNum;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;
    private String email;

    @Column(name = "role")
    private String roleStr;

    public String[] getRoles() {
        return roleStr.split(",");
    }

    public void setRoles(String[] roles) {
        this.roleStr = String.join(",", roles);
    }
}

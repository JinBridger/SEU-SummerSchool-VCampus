package app.vcampus.server.entity;

import app.vcampus.server.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Entity
@Data
@Slf4j
@Table(name = "user")
public class User implements IEntity {
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

    public static User fromMap(Map<String, String> data) {
        try {
            User user = new User();
            user.setCardNum(Integer.parseInt(data.get("cardNum")));
            user.setName(data.get("name"));
            user.setGender(Gender.valueOf(data.get("gender")));
            user.setPhone(data.get("phone"));
            user.setEmail(data.get("email"));
            user.setRoles(data.get("roles").split(","));
            return user;
        } catch (Exception e) {
            log.warn("Failed to parse user from map: {}", data, e);
            return null;
        }
    }

    public String[] getRoles() {
        return roleStr.split(",");
    }

    public void setRoles(String[] roles) {
        this.roleStr = String.join(",", roles);
    }

    public Map<String, String> toMap() {
        return Map.of(
                "cardNum", getCardNum().toString(),
                "gender", getGender().toString(),
                "name", getName(),
                "phone", getPhone(),
                "email", getEmail(),
                "roles", getRoleStr()
        );
    }
}

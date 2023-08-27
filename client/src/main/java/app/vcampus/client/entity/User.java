package app.vcampus.client.entity;

import app.vcampus.client.enums.Gender;
import lombok.Data;

@Data
public class User {
    public Integer cardNum;

    public String name;

    public Gender gender;

    public String phone;
    public String email;

    public String roleStr;

    public String[] getRoles() {
        return roleStr.split(",");
    }

    public void setRoles(String[] roles) {
        this.roleStr = String.join(",", roles);
    }
}

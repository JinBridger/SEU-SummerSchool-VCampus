package app.vcampus.server.utility;

import lombok.Data;

@Data
public class Session {
    String[] roles;

    public boolean permission(String role) {
        if (role.equals("anonymous")) {
            return true;
        } else if (roles == null) {
            return false;
        }

        for (String r : roles) {
            if (r.equals(role) || r.equals("admin")) {
                return true;
            }
        }

        return false;
    }
}

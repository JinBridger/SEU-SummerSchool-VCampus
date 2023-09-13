package app.vcampus.server.utility;

import lombok.Data;

/**
 * Session class.
 */
@Data
public class Session {
    int cardNum;
    String[] roles;

    /**
     * Check if the session has the permission.
     *
     * @param role the role to check
     * @return true if the session has the permission
     */
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

package app.vcampus.server.utility;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Password utility class.
 */
public class Password {
    /**
     * Get the Argon2 password encoder.
     *
     * @return The Argon2 password encoder.
     */
    public static Argon2PasswordEncoder encoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    /**
     * Hash a password.
     *
     * @param password The password to hash.
     * @return The hashed password.
     */
    public static String hash(String password) {
        return encoder().encode(password);
    }

    /**
     * Verify a password.
     *
     * @param password The password to verify.
     * @param hashed The hashed password.
     * @return Whether the password is correct.
     */
    public static boolean verify(String password, String hashed) {
        return encoder().matches(password, hashed);
    }
}

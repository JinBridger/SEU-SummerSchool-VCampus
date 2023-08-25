package app.vcampus.server.utility;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Password {
    public static Argon2PasswordEncoder encoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    }

    public static String hash(String password) {
        return encoder().encode(password);
    }

    public static boolean verify(String password, String hashed) {
        return encoder().matches(password, hashed);
    }
}

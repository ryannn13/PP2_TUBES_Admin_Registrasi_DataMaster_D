package util;

public class PasswordUtil {

    // Cek kecocokan password
    public static boolean checkPassword(String plainTextPassword, String storedPassword) {
        return plainTextPassword.equals(storedPassword);
    }

    public static void main(String[] args) {
        String plainPassword = "mypassword";

        // Simulasi password yang disimpan tanpa hashing
        String storedPassword = plainPassword;

        // Output password
        System.out.println("Password: " + plainPassword);
        System.out.println("Stored Password: " + storedPassword);

        // Cek kecocokan password
        boolean isMatch = checkPassword(plainPassword, storedPassword);
        System.out.println("Password Match: " + isMatch);
    }
}

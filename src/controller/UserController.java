package controller;

import dao.UserDAO;
import model.User;
import java.util.List;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    // Register User
    public boolean registerUser(User user) {
        if (validateUser(user)) {
            // Langsung menggunakan password plain text tanpa hashing
            return userDAO.registerUser(user);
        }
        return false;
    }

    // Authenticate User
    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    // Validate user input
    private boolean validateUser(User user) {
        // Basic validations
        if (user.getUsername() == null || user.getUsername().isEmpty())
            return false;
        if (user.getPassword() == null || user.getPassword().isEmpty())
            return false;
        if (user.getRole() == null || !(user.getRole().equals("masyarakat") || user.getRole().equals("kurir")))
            return false;
        if (user.getNama() == null || user.getNama().isEmpty())
            return false;
        if (user.getAlamat() == null || user.getAlamat().isEmpty())
            return false;
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            return false;
        if (user.getNomorTelepon() == null || !user.getNomorTelepon().matches("^08\\d{9,11}$"))
            return false;
        return true;
    }

    // Approve User
    public boolean approveUser(int userId) {
        return userDAO.updateUserStatus(userId, "active");
    }

    // Reject User
    public boolean rejectUser(int userId) {
        return userDAO.updateUserStatus(userId, "rejected");
    }

    // Delete User
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    // Get Pending Users by Role
    public List<User> getPendingUsers(String role) {
        return userDAO.getUsersByRoleAndStatus(role, "pending");
    }

    // Get Active Users by Role
    public List<User> getActiveUsers(String role) {
        return userDAO.getUsersByRoleAndStatus(role, "active");
    }

    // Get User by ID
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}

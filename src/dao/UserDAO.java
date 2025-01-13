package dao;

import model.User;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Register new user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO Users (username, password, role, status, nama, alamat, email, nomor_telepon) VALUES (?, ?, ?, 'pending', ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // Should be hashed
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getNama());
            pstmt.setString(5, user.getAlamat());
            pstmt.setString(6, user.getEmail());
            pstmt.setString(7, user.getNomorTelepon());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setNama(rs.getString("nama"));
                user.setAlamat(rs.getString("alamat"));
                user.setEmail(rs.getString("email"));
                user.setNomorTelepon(rs.getString("nomor_telepon"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get users by role and status
    public List<User> getUsersByRoleAndStatus(String role, String status) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE role = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setNama(rs.getString("nama"));
                user.setAlamat(rs.getString("alamat"));
                user.setEmail(rs.getString("email"));
                user.setNomorTelepon(rs.getString("nomor_telepon"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update user status (approve/reject)
    public boolean updateUserStatus(int userId, String status) {
        String sql = "UPDATE Users SET status = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, userId);

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update user info
    public boolean updateUserInfo(User user) {
        String sql = "UPDATE Users SET username = ?, password = ?, nama = ?, alamat = ?, email = ?, nomor_telepon = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // Should be hashed
            pstmt.setString(3, user.getNama());
            pstmt.setString(4, user.getAlamat());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getNomorTelepon());
            pstmt.setInt(7, user.getUserId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user by ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setNama(rs.getString("nama"));
                user.setAlamat(rs.getString("alamat"));
                user.setEmail(rs.getString("email"));
                user.setNomorTelepon(rs.getString("nomor_telepon"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Authenticate user
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Should be hashed
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setNama(rs.getString("nama"));
                user.setAlamat(rs.getString("alamat"));
                user.setEmail(rs.getString("email"));
                user.setNomorTelepon(rs.getString("nomor_telepon"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
    private String status;
    private String nama;
    private String alamat;
    private String email;
    private String nomorTelepon;

    public User(String username, String password, String role, String status, String nama, String alamat, String email,
            String nomorTelepon) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}

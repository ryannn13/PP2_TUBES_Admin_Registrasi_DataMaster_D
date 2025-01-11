package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private UserController userController;

    public LoginView() {
        userController = new UserController();
        initialize();
    }

    private void initialize() {
        setTitle("eWaste - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(new JLabel("Username:"), gbc);

        // Username Field
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        // Password Field
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Login Button
        btnLogin = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(btnLogin, gbc);

        // Register Button
        btnRegister = new JButton("Register");
        gbc.gridx = 1;
        panel.add(btnRegister, gbc);

        add(panel);

        // Action Listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterView().setVisible(true);
            }
        });
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userController.authenticateUser(username, password);
        if (user != null) {
            if (user.getStatus().equals("active")) {
                JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + user.getRole(), "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                switch (user.getRole()) {
                    case "masyarakat":
                        new DashboardMasyarakat(user).setVisible(true);
                        break;
                    case "kurir":
                        new DashboardKurir(user).setVisible(true);
                        break;
                    case "admin":
                        new DashboardAdmin(user).setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Role tidak dikenal.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Akun belum diaktifkan atau ditolak.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

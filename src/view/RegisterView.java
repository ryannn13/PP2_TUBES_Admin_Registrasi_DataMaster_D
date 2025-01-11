package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtEmail;
    private JTextField txtNomorTelepon;
    private JButton btnRegister;
    private JButton btnBack;
    private UserController userController;

    public RegisterView() {
        userController = new UserController();
        initialize();
    }

    private void initialize() {
        setTitle("eWaste - Register");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
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

        // Role Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);

        // Role ComboBox
        cmbRole = new JComboBox<>(new String[] { "masyarakat", "kurir" });
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);

        // Nama Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Nama:"), gbc);

        // Nama Field
        txtNama = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNama, gbc);

        // Alamat Label
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Alamat:"), gbc);

        // Alamat Field
        txtAlamat = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtAlamat, gbc);

        // Email Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Email:"), gbc);

        // Email Field
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        // Nomor Telepon Label
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Nomor Telepon:"), gbc);

        // Nomor Telepon Field
        txtNomorTelepon = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtNomorTelepon, gbc);

        // Register Button
        btnRegister = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(btnRegister, gbc);

        // Back Button
        btnBack = new JButton("Back to Login");
        gbc.gridx = 1;
        panel.add(btnBack, gbc);

        add(panel);

        // Action Listeners
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
    }

    private void register() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String role = (String) cmbRole.getSelectedItem();
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String email = txtEmail.getText();
        String nomorTelepon = txtNomorTelepon.getText();

        User user = new User(username, password, role, "pending", nama, alamat, email, nomorTelepon);
        boolean success = userController.registerUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil! Menunggu persetujuan admin.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registrasi gagal. Periksa kembali input Anda.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

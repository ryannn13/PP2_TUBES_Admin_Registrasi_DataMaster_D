package view;

import controller.PenjemputanController;
import controller.PointsController;
import controller.SampahController;
import controller.KategoriSampahController;
import dao.SampahDAO;
import model.User;
import model.Penjemputan;
import model.Sampah;
import model.KategoriSampah;
import model.Points;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DashboardMasyarakat extends JFrame {
    private User masyarakatUser;
    private PenjemputanController penjemputanController;
    private PointsController pointsController;
    private SampahController sampahController;
    private KategoriSampahController kategoriSampahController;

    private JButton btnRequestPenjemputan;
    private JTable tableHistoryPenjemputan;
    private JLabel lblTotalPoints;

    public DashboardMasyarakat(User user) {
        this.masyarakatUser = user;
        penjemputanController = new PenjemputanController();
        pointsController = new PointsController();
        sampahController = new SampahController();
        kategoriSampahController = new KategoriSampahController();
        initialize();
    }

    private void initialize() {
        setTitle("eWaste - Dashboard Masyarakat");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel(new BorderLayout());

        // Top Panel for Points
        JPanel topPanel = new JPanel();
        lblTotalPoints = new JLabel("Total Points: " + pointsController.getTotalPoints(masyarakatUser.getUserId()));
        topPanel.add(lblTotalPoints);
        panel.add(topPanel, BorderLayout.NORTH);

        // Center Panel for History Penjemputan
        tableHistoryPenjemputan = new JTable();
        loadHistoryPenjemputan();
        JScrollPane scrollHistory = new JScrollPane(tableHistoryPenjemputan);
        panel.add(scrollHistory, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel();
        btnRequestPenjemputan = new JButton("Request Penjemputan");
        bottomPanel.add(btnRequestPenjemputan);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Action Listeners
        btnRequestPenjemputan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestPenjemputan();
            }
        });
    }

    // Load History Penjemputan
    private void loadHistoryPenjemputan() {
        List<Penjemputan> penjemputans = penjemputanController.getPenjemputanByStatus("selesai"); // atau status lain
                                                                                                  // sesuai kebutuhan
        DefaultTableModel model = new DefaultTableModel(
                new Object[] { "ID", "Status", "Tanggal Request", "Tanggal Selesai" }, 0);
        for (Penjemputan penjemputan : penjemputans) {
            if (penjemputan.getMasyarakatId() == masyarakatUser.getUserId()) {
                model.addRow(new Object[] {
                        penjemputan.getPenjemputanId(),
                        penjemputan.getStatus(),
                        penjemputan.getTanggalRequest(),
                        penjemputan.getTanggalSelesai()
                });
            }
        }
        tableHistoryPenjemputan.setModel(model);
    }

    // Request Penjemputan
    private void requestPenjemputan() {
        // Input Nama Sampah
        JTextField txtNamaSampah = new JTextField();
        // Input Jumlah Sampah
        JTextField txtJumlahSampah = new JTextField();
        // Input Berat Sampah
        JTextField txtBeratSampah = new JTextField();
        // Pilih Kategori Sampah
        List<KategoriSampah> kategoris = kategoriSampahController.getAllKategoriSampah();
        String[] kategoriOptions = new String[kategoris.size()];
        for (int i = 0; i < kategoris.size(); i++) {
            kategoriOptions[i] = kategoris.get(i).getKategoriId() + " - " + kategoris.get(i).getNamaKategori();
        }
        JComboBox<String> cmbKategoriSampah = new JComboBox<>(kategoriOptions);

        Object[] message = {
                "Nama Sampah:", txtNamaSampah,
                "Jumlah Sampah:", txtJumlahSampah,
                "Berat Sampah (kg):", txtBeratSampah,
                "Kategori Sampah:", cmbKategoriSampah
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Request Penjemputan", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String namaSampah = txtNamaSampah.getText();
            int jumlahSampah;
            double beratSampah;
            int kategoriId;

            try {
                jumlahSampah = Integer.parseInt(txtJumlahSampah.getText());
                beratSampah = Double.parseDouble(txtBeratSampah.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah dan berat sampah harus angka.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedKategori = (String) cmbKategoriSampah.getSelectedItem();
            if (selectedKategori != null) {
                kategoriId = Integer.parseInt(selectedKategori.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih kategori sampah.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buat entri sampah baru
            Sampah sampah = new Sampah(namaSampah, jumlahSampah, beratSampah, kategoriId);
            boolean sampahCreated = sampahController.createSampah(sampah);
            if (!sampahCreated) {
                JOptionPane.showMessageDialog(this, "Gagal membuat entri sampah.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ambil sampah_id terbaru
            List<Sampah> sampahs = sampahController.getAllSampah();
            int latestSampahId = sampahs.get(sampahs.size() - 1).getSampahId();

            // Buat penjemputan
            Penjemputan penjemputan = new Penjemputan();
            penjemputan.setMasyarakatId(masyarakatUser.getUserId());
            penjemputan.setSampahId(latestSampahId);
            boolean penjemputanCreated = penjemputanController.createPenjemputan(penjemputan);

            if (penjemputanCreated) {
                JOptionPane.showMessageDialog(this, "Penjemputan berhasil diajukan.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadHistoryPenjemputan();
                lblTotalPoints.setText("Total Points: " + pointsController.getTotalPoints(masyarakatUser.getUserId()));
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengajukan penjemputan.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

package view;

import controller.PenjemputanController;
import controller.DropboxController;
import controller.PointsController;
import controller.KategoriSampahController;
import dao.SampahDAO;
import model.User;
import model.Penjemputan;
import model.Dropbox;
import model.Sampah;
import model.KategoriSampah;
import model.Points;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Date;

public class DashboardKurir extends JFrame {
    private User kurirUser;
    private PenjemputanController penjemputanController;
    private DropboxController dropboxController;
    private SampahDAO sampahDAO;
    private PointsController pointsController;
    private KategoriSampahController kategoriSampahController;

    private JTable tablePenjemputan;
    private JButton btnJemput;
    private JButton btnSelesai;
    private JLabel lblTotalPoints;

    public DashboardKurir(User user) {
        this.kurirUser = user;
        penjemputanController = new PenjemputanController();
        dropboxController = new DropboxController();
        sampahDAO = new SampahDAO();
        pointsController = new PointsController();
        kategoriSampahController = new KategoriSampahController();
        initialize();
    }

    private void initialize() {
        setTitle("eWaste - Dashboard Kurir");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel(new BorderLayout());

        // Top Panel for Points
        JPanel topPanel = new JPanel();
        lblTotalPoints = new JLabel("Total Points: " + pointsController.getTotalPoints(getMasyarakatId()));
        topPanel.add(lblTotalPoints);
        panel.add(topPanel, BorderLayout.NORTH);

        // Center Panel for Penjemputan List
        tablePenjemputan = new JTable();
        loadPenjemputan();
        JScrollPane scrollPenjemputan = new JScrollPane(tablePenjemputan);
        panel.add(scrollPenjemputan, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel();
        btnJemput = new JButton("Jemput");
        btnSelesai = new JButton("Selesai");
        bottomPanel.add(btnJemput);
        bottomPanel.add(btnSelesai);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Action Listeners
        btnJemput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jemput();
            }
        });

        btnSelesai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selesai();
            }
        });
    }

    // Load Penjemputan
    private void loadPenjemputan() {
        List<Penjemputan> penjemputans = penjemputanController.getPenjemputanByStatus("pending");
        penjemputans.addAll(penjemputanController.getPenjemputanByStatus("dalam penjemputan"));
        penjemputans.addAll(penjemputanController.getPenjemputanByStatus("selesai"));

        DefaultTableModel model = new DefaultTableModel(
                new Object[] { "ID", "Masyarakat ID", "Sampah ID", "Status", "Tanggal Request", "Tanggal Selesai" }, 0);
        for (Penjemputan penjemputan : penjemputans) {
            model.addRow(new Object[] {
                    penjemputan.getPenjemputanId(),
                    penjemputan.getMasyarakatId(),
                    penjemputan.getSampahId(),
                    penjemputan.getStatus(),
                    penjemputan.getTanggalRequest(),
                    penjemputan.getTanggalSelesai()
            });
        }
        tablePenjemputan.setModel(model);
    }

    // Jemput Penjemputan
    private void jemput() {
        int selectedRow = tablePenjemputan.getSelectedRow();
        if (selectedRow >= 0) {
            String status = (String) tablePenjemputan.getValueAt(selectedRow, 3);
            if (!status.equals("pending")) {
                JOptionPane.showMessageDialog(this, "Penjemputan ini sudah dalam proses atau selesai.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int penjemputanId = (int) tablePenjemputan.getValueAt(selectedRow, 0);
            boolean success = penjemputanController.assignKurir(penjemputanId, kurirUser.getUserId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Penjemputan sedang dalam proses.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadPenjemputan();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menjemput penjemputan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih penjemputan terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Selesai Penjemputan

    private void selesai() {
        int selectedRow = tablePenjemputan.getSelectedRow();
        if (selectedRow >= 0) {
            String status = (String) tablePenjemputan.getValueAt(selectedRow, 3);
            if (!status.equals("dalam penjemputan")) {
                JOptionPane.showMessageDialog(this, "Penjemputan ini belum dalam proses.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int penjemputanId = (int) tablePenjemputan.getValueAt(selectedRow, 0);

            // Pilih Dropbox
            List<Dropbox> dropboxes = dropboxController.getAllDropbox();
            if (dropboxes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tidak ada dropbox yang tersedia. Tambahkan dropbox terlebih dahulu.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] dropboxOptions = new String[dropboxes.size()];
            for (int i = 0; i < dropboxes.size(); i++) {
                dropboxOptions[i] = dropboxes.get(i).getDropboxId() + " - " + dropboxes.get(i).getLokasi();
            }
            String selectedDropbox = (String) JOptionPane.showInputDialog(this, "Pilih Dropbox:", "Dropbox",
                    JOptionPane.PLAIN_MESSAGE, null, dropboxOptions, dropboxOptions[0]);
            if (selectedDropbox != null) {
                int dropboxId = Integer.parseInt(selectedDropbox.split(" - ")[0]);
                Date tanggalSelesai = new Date(System.currentTimeMillis()); // Menggunakan java.sql.Date

                // Update status to 'selesai'
                boolean success = penjemputanController.updatePenjemputanStatus(penjemputanId, "selesai",
                        kurirUser.getUserId(), dropboxId, tanggalSelesai);
                if (success) {
                    // Tambah points
                    int sampahId = (int) tablePenjemputan.getValueAt(selectedRow, 2);
                    Sampah sampah = sampahDAO.getSampahById(sampahId);
                    if (sampah != null) {
                        int kategoriId = sampah.getKategoriId();
                        KategoriSampah kategori = kategoriSampahController.getKategoriSampahById(kategoriId);
                        if (kategori != null) {
                            int poin = kategori.getPoin();
                            // Mendapatkan masyarakat_id dari penjemputan
                            List<Penjemputan> penjemputans = penjemputanController.getPenjemputanByStatus("selesai");
                            int masyarakatId = -1;
                            for (Penjemputan p : penjemputans) {
                                if (p.getPenjemputanId() == penjemputanId) {
                                    masyarakatId = p.getMasyarakatId();
                                    break;
                                }
                            }
                            if (masyarakatId != -1) {
                                Points points = new Points(masyarakatId, poin);
                                pointsController.addPoints(points);
                                lblTotalPoints
                                        .setText("Total Points: " + pointsController.getTotalPoints(masyarakatId));
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(this, "Penjemputan selesai.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadPenjemputan();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyelesaikan penjemputan.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih penjemputan terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Mendapatkan masyarakat_id dari penjemputan
    private int getMasyarakatId() {
        // Implementasi sesuai kebutuhan, misalnya menyimpan last masyarakat_id yang
        // diajukan
        // Atau mengambil dari penjemputan yang sedang diproses
        // Untuk contoh ini, kita akan mengembalikan 0 sebagai placeholder
        return 0; // Ganti dengan implementasi yang sesuai
    }
}

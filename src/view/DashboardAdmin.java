package view;

import controller.UserController;
import controller.DropboxController;
import controller.SampahController;
import controller.KategoriSampahController;
import controller.PenjemputanController; // Import PenjemputanController
import model.User;
import model.Dropbox;
import model.Sampah;
import model.KategoriSampah;
import model.Penjemputan; // Import model Penjemputan

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// Tambahkan import untuk renderer dan editor
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;

public class DashboardAdmin extends JFrame {
    private User adminUser;
    private UserController userController;
    private SampahController sampahController;
    private DropboxController dropboxController;
    private KategoriSampahController kategoriSampahController;
    private PenjemputanController penjemputanController; // Tambahkan PenjemputanController

    // Approval Masyarakat Components
    private JTable tablePendingMasyarakat;
    // Hapus tombol approve dan reject masyarakat
    // private JButton btnApproveMasyarakat;
    // private JButton btnRejectMasyarakat;

    // Approval Kurir Components
    private JTable tablePendingKurir;
    // Hapus tombol approve dan reject kurir
    // private JButton btnApproveKurir;
    // private JButton btnRejectKurir;

    // Pengelolaan Sampah Components
    private JTable tableSampah;
    private JButton btnTambahSampah;
    private JButton btnUbahSampah;
    private JButton btnHapusSampah;

    // Dropbox Components
    private JTable tableDropbox;
    private JButton btnTambahDropbox;
    private JButton btnUbahDropbox;
    private JButton btnHapusDropbox;

    // Kategori Sampah Components
    private JTable tableKategoriSampah;
    private JButton btnTambahKategori;
    private JButton btnUbahKategori;
    private JButton btnHapusKategori;

    // Daftar Masyarakat Components
    private JTable tableDaftarMasyarakat;

    // Daftar Kurir Components
    private JTable tableDaftarKurir;

    // Daftar Penjemputan Sampah Components
    private JTable tableDaftarPenjemputan;
    private JButton btnRefreshPenjemputan; // Tombol Refresh (Opsional)

    // Renderer dan Editor
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnApprove = new JButton("Setujui");
        private JButton btnReview = new JButton("Review");
        private JButton btnReject = new JButton("Tolak");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            add(btnApprove);
            add(btnReview);
            add(btnReject);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        private JButton btnApprove = new JButton("Setujui");
        private JButton btnReview = new JButton("Review");
        private JButton btnReject = new JButton("Tolak");
        private int currentRow;
        private String role; // "masyarakat" atau "kurir"

        public ButtonEditor(JTable table, String role) {
            this.role = role;
            panel.add(btnApprove);
            panel.add(btnReview);
            panel.add(btnReject);

            // Action Listener untuk tombol Setujui
            btnApprove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (role.equals("masyarakat")) {
                        approveMasyarakat(currentRow);
                    } else if (role.equals("kurir")) {
                        approveKurir(currentRow);
                    }
                }
            });

            // Action Listener untuk tombol Review
            btnReview.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (role.equals("masyarakat")) {
                        reviewUser(currentRow);
                    } else if (role.equals("kurir")) {
                        reviewKurir(currentRow);
                    }
                }
            });

            // Action Listener untuk tombol Tolak
            btnReject.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (role.equals("masyarakat")) {
                        rejectMasyarakat(currentRow);
                    } else if (role.equals("kurir")) {
                        rejectKurir(currentRow);
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    public DashboardAdmin(User admin) {
        this.adminUser = admin;
        userController = new UserController();
        sampahController = new SampahController();
        dropboxController = new DropboxController();
        kategoriSampahController = new KategoriSampahController();
        penjemputanController = new PenjemputanController(); // Inisialisasi PenjemputanController
        initialize();
    }

    private void initialize() {
        setTitle("eWaste - Dashboard Admin");
        setSize(1200, 800); // Sesuaikan ukuran jika perlu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Approval Masyarakat
        JPanel panelApprovalMasyarakat = new JPanel(new BorderLayout());
        tablePendingMasyarakat = new JTable();
        loadPendingMasyarakat();
        JScrollPane scrollPendingMasyarakat = new JScrollPane(tablePendingMasyarakat);
        panelApprovalMasyarakat.add(new JLabel("Pending Masyarakat"), BorderLayout.NORTH);
        panelApprovalMasyarakat.add(scrollPendingMasyarakat, BorderLayout.CENTER);

        // Hapus panelButtonsMasyarakat karena tombol sudah di tabel
        JPanel panelButtonsMasyarakat = new JPanel();
        panelApprovalMasyarakat.add(panelButtonsMasyarakat, BorderLayout.SOUTH);

        // Tab Approval Kurir
        JPanel panelApprovalKurir = new JPanel(new BorderLayout());
        tablePendingKurir = new JTable();
        loadPendingKurir();
        JScrollPane scrollPendingKurir = new JScrollPane(tablePendingKurir);
        panelApprovalKurir.add(new JLabel("Pending Kurir"), BorderLayout.NORTH);
        panelApprovalKurir.add(scrollPendingKurir, BorderLayout.CENTER);

        // Hapus panelButtonsKurir karena tombol sudah di tabel
        JPanel panelButtonsKurir = new JPanel();
        panelApprovalKurir.add(panelButtonsKurir, BorderLayout.SOUTH);

        // Tab Pengelolaan Sampah
        JPanel panelPengelolaanSampah = new JPanel(new BorderLayout());
        tableSampah = new JTable();
        loadSampah();
        JScrollPane scrollSampah = new JScrollPane(tableSampah);
        panelPengelolaanSampah.add(new JLabel("Pengelolaan Sampah"), BorderLayout.NORTH);
        panelPengelolaanSampah.add(scrollSampah, BorderLayout.CENTER);

        // Buttons
        JPanel panelButtonsSampah = new JPanel();
        btnTambahSampah = new JButton("Tambah");
        btnUbahSampah = new JButton("Ubah");
        btnHapusSampah = new JButton("Hapus");
        panelButtonsSampah.add(btnTambahSampah);
        panelButtonsSampah.add(btnUbahSampah);
        panelButtonsSampah.add(btnHapusSampah);
        panelPengelolaanSampah.add(panelButtonsSampah, BorderLayout.SOUTH);

        // Tab Dropbox
        JPanel panelDropbox = new JPanel(new BorderLayout());
        tableDropbox = new JTable();
        loadDropbox();
        JScrollPane scrollDropbox = new JScrollPane(tableDropbox);
        panelDropbox.add(new JLabel("Dropbox Management"), BorderLayout.NORTH);
        panelDropbox.add(scrollDropbox, BorderLayout.CENTER);

        // Buttons
        JPanel panelButtonsDropbox = new JPanel();
        btnTambahDropbox = new JButton("Tambah");
        btnUbahDropbox = new JButton("Ubah");
        btnHapusDropbox = new JButton("Hapus");
        panelButtonsDropbox.add(btnTambahDropbox);
        panelButtonsDropbox.add(btnUbahDropbox);
        panelButtonsDropbox.add(btnHapusDropbox);
        panelDropbox.add(panelButtonsDropbox, BorderLayout.SOUTH);

        // Tab Kategori Sampah
        JPanel panelKategoriSampah = new JPanel(new BorderLayout());
        tableKategoriSampah = new JTable();
        loadKategoriSampah();
        JScrollPane scrollKategoriSampah = new JScrollPane(tableKategoriSampah);
        panelKategoriSampah.add(new JLabel("Kategori Sampah"), BorderLayout.NORTH);
        panelKategoriSampah.add(scrollKategoriSampah, BorderLayout.CENTER);

        // Buttons
        JPanel panelButtonsKategori = new JPanel();
        btnTambahKategori = new JButton("Tambah Kategori");
        btnUbahKategori = new JButton("Ubah Kategori");
        btnHapusKategori = new JButton("Hapus Kategori");
        panelButtonsKategori.add(btnTambahKategori);
        panelButtonsKategori.add(btnUbahKategori);
        panelButtonsKategori.add(btnHapusKategori);
        panelKategoriSampah.add(panelButtonsKategori, BorderLayout.SOUTH);

        // Tab Daftar Masyarakat
        JPanel panelDaftarMasyarakat = new JPanel(new BorderLayout());
        tableDaftarMasyarakat = new JTable();
        loadDaftarMasyarakat();
        JScrollPane scrollDaftarMasyarakat = new JScrollPane(tableDaftarMasyarakat);
        panelDaftarMasyarakat.add(new JLabel("Daftar Masyarakat"), BorderLayout.NORTH);
        panelDaftarMasyarakat.add(scrollDaftarMasyarakat, BorderLayout.CENTER);

        // Tab Daftar Kurir
        JPanel panelDaftarKurir = new JPanel(new BorderLayout());
        tableDaftarKurir = new JTable();
        loadDaftarKurir();
        JScrollPane scrollDaftarKurir = new JScrollPane(tableDaftarKurir);
        panelDaftarKurir.add(new JLabel("Daftar Kurir"), BorderLayout.NORTH);
        panelDaftarKurir.add(scrollDaftarKurir, BorderLayout.CENTER);

        // Tab Daftar Penjemputan Sampah
        JPanel panelDaftarPenjemputan = new JPanel(new BorderLayout());
        tableDaftarPenjemputan = new JTable();
        loadDaftarPenjemputan(); // Memuat data penjemputan
        JScrollPane scrollDaftarPenjemputan = new JScrollPane(tableDaftarPenjemputan);
        panelDaftarPenjemputan.add(new JLabel("Daftar Penjemputan Sampah"), BorderLayout.NORTH);
        panelDaftarPenjemputan.add(scrollDaftarPenjemputan, BorderLayout.CENTER);

        // Tombol Refresh (Opsional)
        JPanel panelButtonsPenjemputan = new JPanel();
        btnRefreshPenjemputan = new JButton("Refresh");
        panelButtonsPenjemputan.add(btnRefreshPenjemputan);
        panelDaftarPenjemputan.add(panelButtonsPenjemputan, BorderLayout.SOUTH);

        // Add Tabs
        tabbedPane.addTab("Approval Masyarakat", panelApprovalMasyarakat);
        tabbedPane.addTab("Approval Kurir", panelApprovalKurir);
        tabbedPane.addTab("Pengelolaan Sampah", panelPengelolaanSampah);
        tabbedPane.addTab("Dropbox", panelDropbox);
        tabbedPane.addTab("Kategori Sampah", panelKategoriSampah);
        tabbedPane.addTab("Daftar Masyarakat", panelDaftarMasyarakat);
        tabbedPane.addTab("Daftar Kurir", panelDaftarKurir);
        tabbedPane.addTab("Daftar Penjemputan Sampah", panelDaftarPenjemputan); // Tambahkan tab baru

        add(tabbedPane);

        // Action Listeners for Sampah
        btnTambahSampah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tambahSampah();
            }
        });

        btnUbahSampah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ubahSampah();
            }
        });

        btnHapusSampah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusSampah();
            }
        });

        // Action Listeners for Dropbox
        btnTambahDropbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tambahDropbox();
            }
        });

        btnUbahDropbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ubahDropbox();
            }
        });

        btnHapusDropbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusDropbox();
            }
        });

        // Action Listeners for Kategori Sampah
        btnTambahKategori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tambahKategori();
            }
        });

        btnUbahKategori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ubahKategori();
            }
        });

        btnHapusKategori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusKategori();
            }
        });

        // Action Listener for Refresh Penjemputan
        btnRefreshPenjemputan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDaftarPenjemputan();
            }
        });
    }

    // Load Pending Masyarakat
    private void loadPendingMasyarakat() {
        List<User> pendingMasyarakat = userController.getPendingUsers("masyarakat");
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Username", "Nama", "Aksi" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Hanya kolom Aksi yang bisa diedit
            }
        };
        for (User user : pendingMasyarakat) {
            model.addRow(new Object[] { user.getUserId(), user.getUsername(), user.getNama(), "Aksi" });
        }
        tablePendingMasyarakat.setModel(model);

        // Set Renderer dan Editor untuk kolom Aksi
        tablePendingMasyarakat.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        tablePendingMasyarakat.getColumn("Aksi").setCellEditor(new ButtonEditor(tablePendingMasyarakat, "masyarakat"));
    }

    // Load Pending Kurir
    private void loadPendingKurir() {
        List<User> pendingKurir = userController.getPendingUsers("kurir");
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Username", "Nama", "Aksi" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Hanya kolom Aksi yang bisa diedit
            }
        };
        for (User user : pendingKurir) {
            model.addRow(new Object[] { user.getUserId(), user.getUsername(), user.getNama(), "Aksi" });
        }
        tablePendingKurir.setModel(model);

        // Set Renderer dan Editor untuk kolom Aksi
        tablePendingKurir.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        tablePendingKurir.getColumn("Aksi").setCellEditor(new ButtonEditor(tablePendingKurir, "kurir"));
    }

    // Load Sampah
    private void loadSampah() {
        List<Sampah> sampahs = sampahController.getAllSampah();
        DefaultTableModel model = new DefaultTableModel(
                new Object[] { "ID", "Nama Sampah", "Jumlah", "Berat (kg)", "Kategori ID" }, 0);
        for (Sampah sampah : sampahs) {
            model.addRow(new Object[] { sampah.getSampahId(), sampah.getNamaSampah(), sampah.getJumlahSampah(),
                    sampah.getBeratSampah(), sampah.getKategoriId() });
        }
        tableSampah.setModel(model);
    }

    // Load Dropbox
    private void loadDropbox() {
        List<Dropbox> dropboxes = dropboxController.getAllDropbox();
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Lokasi", "Kapasitas" }, 0);
        for (Dropbox dropbox : dropboxes) {
            model.addRow(new Object[] { dropbox.getDropboxId(), dropbox.getLokasi(), dropbox.getKapasitas() });
        }
        tableDropbox.setModel(model);
    }

    // Load Kategori Sampah
    private void loadKategoriSampah() {
        List<KategoriSampah> kategoris = kategoriSampahController.getAllKategoriSampah();
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Nama Kategori", "Poin" }, 0);
        for (KategoriSampah kategori : kategoris) {
            model.addRow(new Object[] { kategori.getKategoriId(), kategori.getNamaKategori(), kategori.getPoin() });
        }
        tableKategoriSampah.setModel(model);
    }

    // Load Daftar Masyarakat
    private void loadDaftarMasyarakat() {
        List<User> masyarakat = userController.getActiveUsers("masyarakat");
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Username", "Nama", "Email", "Telepon" },
                0);
        for (User user : masyarakat) {
            model.addRow(new Object[] { user.getUserId(), user.getUsername(), user.getNama(), user.getEmail(),
                    user.getNomorTelepon() });
        }
        tableDaftarMasyarakat.setModel(model);
    }

    // Load Daftar Kurir
    private void loadDaftarKurir() {
        List<User> kurir = userController.getActiveUsers("kurir");
        DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "Username", "Nama", "Email", "Telepon" },
                0);
        for (User user : kurir) {
            model.addRow(new Object[] { user.getUserId(), user.getUsername(), user.getNama(), user.getEmail(),
                    user.getNomorTelepon() });
        }
        tableDaftarKurir.setModel(model);
    }

    // Load Daftar Penjemputan Sampah
    private void loadDaftarPenjemputan() {
        List<Penjemputan> penjemputans = penjemputanController.getAllPenjemputan();

        DefaultTableModel model = new DefaultTableModel(
                new Object[] { "ID", "Masyarakat ID", "Sampah ID", "Status", "Tanggal Request", "Tanggal Selesai",
                        "Kurir ID", "Dropbox ID" },
                0);

        for (Penjemputan penjemputan : penjemputans) {
            model.addRow(new Object[] {
                    penjemputan.getPenjemputanId(),
                    penjemputan.getMasyarakatId(),
                    penjemputan.getSampahId(),
                    penjemputan.getStatus(),
                    penjemputan.getTanggalRequest(),
                    penjemputan.getTanggalSelesai(),
                    penjemputan.getKurirId(),
                    penjemputan.getDropboxId()
            });
        }

        tableDaftarPenjemputan.setModel(model);
    }

    // Approve Masyarakat
    private void approveMasyarakat(int row) {
        int userId = (int) tablePendingMasyarakat.getValueAt(row, 0);
        boolean success = userController.approveUser(userId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Masyarakat disetujui.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPendingMasyarakat();
            loadDaftarMasyarakat();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyetujui masyarakat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reject Masyarakat
    private void rejectMasyarakat(int row) {
        int userId = (int) tablePendingMasyarakat.getValueAt(row, 0);
        boolean success = userController.rejectUser(userId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Masyarakat ditolak.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPendingMasyarakat();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menolak masyarakat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Approve Kurir
    private void approveKurir(int row) {
        int userId = (int) tablePendingKurir.getValueAt(row, 0);
        boolean success = userController.approveUser(userId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Kurir disetujui.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPendingKurir();
            loadDaftarKurir();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyetujui kurir.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reject Kurir
    private void rejectKurir(int row) {
        int userId = (int) tablePendingKurir.getValueAt(row, 0);
        boolean success = userController.rejectUser(userId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Kurir ditolak.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPendingKurir();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menolak kurir.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Review User (Masyarakat)
    private void reviewUser(int row) {
        int userId = (int) tablePendingMasyarakat.getValueAt(row, 0);
        User user = userController.getUserById(userId);

        if (user != null) {
            // Buat dialog popup
            JDialog dialog = new JDialog(this, "Detail Masyarakat", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            // Panel untuk detail
            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            panel.add(new JLabel("ID:"));
            panel.add(new JLabel(String.valueOf(user.getUserId())));

            panel.add(new JLabel("Username:"));
            panel.add(new JLabel(user.getUsername()));

            panel.add(new JLabel("Nama:"));
            panel.add(new JLabel(user.getNama()));

            panel.add(new JLabel("Alamat:"));
            panel.add(new JLabel(user.getAlamat()));

            panel.add(new JLabel("Email:"));
            panel.add(new JLabel(user.getEmail()));

            panel.add(new JLabel("Nomor Telepon:"));
            panel.add(new JLabel(user.getNomorTelepon()));

            dialog.add(panel, BorderLayout.CENTER);

            // Tombol Tutup
            JButton btnClose = new JButton("Tutup");
            btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            JPanel panelButton = new JPanel();
            panelButton.add(btnClose);
            dialog.add(panelButton, BorderLayout.SOUTH);

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Detail pengguna tidak ditemukan.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Review User (Kurir)
    private void reviewKurir(int row) {
        int userId = (int) tablePendingKurir.getValueAt(row, 0);
        User user = userController.getUserById(userId);

        if (user != null) {
            // Buat dialog popup
            JDialog dialog = new JDialog(this, "Detail Kurir", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            // Panel untuk detail
            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            panel.add(new JLabel("ID:"));
            panel.add(new JLabel(String.valueOf(user.getUserId())));

            panel.add(new JLabel("Username:"));
            panel.add(new JLabel(user.getUsername()));

            panel.add(new JLabel("Nama:"));
            panel.add(new JLabel(user.getNama()));

            panel.add(new JLabel("Alamat:"));
            panel.add(new JLabel(user.getAlamat()));

            panel.add(new JLabel("Email:"));
            panel.add(new JLabel(user.getEmail()));

            panel.add(new JLabel("Nomor Telepon:"));
            panel.add(new JLabel(user.getNomorTelepon()));

            dialog.add(panel, BorderLayout.CENTER);

            // Tombol Tutup
            JButton btnClose = new JButton("Tutup");
            btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            JPanel panelButton = new JPanel();
            panelButton.add(btnClose);
            dialog.add(panelButton, BorderLayout.SOUTH);

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Detail pengguna tidak ditemukan.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tambah Sampah
    private void tambahSampah() {
        JTextField txtNamaSampah = new JTextField();
        JTextField txtJumlahSampah = new JTextField();
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

        int option = JOptionPane.showConfirmDialog(this, message, "Tambah Sampah", JOptionPane.OK_CANCEL_OPTION);
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

            Sampah sampah = new Sampah(namaSampah, jumlahSampah, beratSampah, kategoriId);
            boolean success = sampahController.createSampah(sampah);
            if (success) {
                JOptionPane.showMessageDialog(this, "Sampah ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadSampah();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan sampah.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Ubah Sampah
    private void ubahSampah() {
        int selectedRow = tableSampah.getSelectedRow();
        if (selectedRow >= 0) {
            int sampahId = (int) tableSampah.getValueAt(selectedRow, 0);
            String currentNamaSampah = (String) tableSampah.getValueAt(selectedRow, 1);
            int currentJumlah = (int) tableSampah.getValueAt(selectedRow, 2);
            double currentBerat = (double) tableSampah.getValueAt(selectedRow, 3);
            int currentKategoriId = (int) tableSampah.getValueAt(selectedRow, 4);

            JTextField txtNamaSampah = new JTextField(currentNamaSampah);
            JTextField txtJumlahSampah = new JTextField(String.valueOf(currentJumlah));
            JTextField txtBeratSampah = new JTextField(String.valueOf(currentBerat));

            // Pilih Kategori Sampah
            List<KategoriSampah> kategoris = kategoriSampahController.getAllKategoriSampah();
            String[] kategoriOptions = new String[kategoris.size()];
            for (int i = 0; i < kategoris.size(); i++) {
                kategoriOptions[i] = kategoris.get(i).getKategoriId() + " - " + kategoris.get(i).getNamaKategori();
            }
            JComboBox<String> cmbKategoriSampah = new JComboBox<>(kategoriOptions);
            // Set selected kategori
            for (int i = 0; i < kategoris.size(); i++) {
                if (kategoris.get(i).getKategoriId() == currentKategoriId) {
                    cmbKategoriSampah.setSelectedIndex(i);
                    break;
                }
            }

            Object[] message = {
                    "Nama Sampah:", txtNamaSampah,
                    "Jumlah Sampah:", txtJumlahSampah,
                    "Berat Sampah (kg):", txtBeratSampah,
                    "Kategori Sampah:", cmbKategoriSampah
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ubah Sampah", JOptionPane.OK_CANCEL_OPTION);
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

                Sampah sampah = new Sampah(namaSampah, jumlahSampah, beratSampah, kategoriId);
                sampah.setSampahId(sampahId);
                boolean success = sampahController.updateSampah(sampah);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Sampah diubah.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadSampah();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengubah sampah.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih sampah terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Hapus Sampah
    private void hapusSampah() {
        int selectedRow = tableSampah.getSelectedRow();
        if (selectedRow >= 0) {
            int sampahId = (int) tableSampah.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus sampah ini?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = sampahController.deleteSampah(sampahId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Sampah dihapus.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadSampah();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus sampah.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih sampah terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Tambah Dropbox
    private void tambahDropbox() {
        JTextField txtLokasi = new JTextField();
        JTextField txtKapasitas = new JTextField();

        Object[] message = {
                "Lokasi:", txtLokasi,
                "Kapasitas:", txtKapasitas
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tambah Dropbox", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String lokasi = txtLokasi.getText();
            int kapasitas;
            try {
                kapasitas = Integer.parseInt(txtKapasitas.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kapasitas harus angka.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Dropbox dropbox = new Dropbox(lokasi, kapasitas);
            boolean success = dropboxController.createDropbox(dropbox);
            if (success) {
                JOptionPane.showMessageDialog(this, "Dropbox ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDropbox();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan dropbox.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Ubah Dropbox
    private void ubahDropbox() {
        int selectedRow = tableDropbox.getSelectedRow();
        if (selectedRow >= 0) {
            int dropboxId = (int) tableDropbox.getValueAt(selectedRow, 0);
            String currentLokasi = (String) tableDropbox.getValueAt(selectedRow, 1);
            int currentKapasitas = (int) tableDropbox.getValueAt(selectedRow, 2);

            JTextField txtLokasi = new JTextField(currentLokasi);
            JTextField txtKapasitas = new JTextField(String.valueOf(currentKapasitas));

            Object[] message = {
                    "Lokasi:", txtLokasi,
                    "Kapasitas:", txtKapasitas
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ubah Dropbox", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String lokasi = txtLokasi.getText();
                int kapasitas;
                try {
                    kapasitas = Integer.parseInt(txtKapasitas.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Kapasitas harus angka.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Dropbox dropbox = new Dropbox(lokasi, kapasitas);
                dropbox.setDropboxId(dropboxId);
                boolean success = dropboxController.updateDropbox(dropbox);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Dropbox diubah.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadDropbox();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengubah dropbox.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih dropbox terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Hapus Dropbox
    private void hapusDropbox() {
        int selectedRow = tableDropbox.getSelectedRow();
        if (selectedRow >= 0) {
            int dropboxId = (int) tableDropbox.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus dropbox ini?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = dropboxController.deleteDropbox(dropboxId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Dropbox dihapus.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadDropbox();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus dropbox.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih dropbox terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Tambah Kategori Sampah
    private void tambahKategori() {
        JTextField txtNamaKategori = new JTextField();
        JTextField txtPoin = new JTextField();

        Object[] message = {
                "Nama Kategori:", txtNamaKategori,
                "Poin:", txtPoin
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tambah Kategori Sampah",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String namaKategori = txtNamaKategori.getText();
            int poin;
            try {
                poin = Integer.parseInt(txtPoin.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Poin harus angka.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KategoriSampah kategori = new KategoriSampah(namaKategori, poin);
            boolean success = kategoriSampahController.createKategoriSampah(kategori);
            if (success) {
                JOptionPane.showMessageDialog(this, "Kategori sampah ditambahkan.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadKategoriSampah();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan kategori sampah.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Ubah Kategori Sampah
    private void ubahKategori() {
        int selectedRow = tableKategoriSampah.getSelectedRow();
        if (selectedRow >= 0) {
            int kategoriId = (int) tableKategoriSampah.getValueAt(selectedRow, 0);
            String currentNamaKategori = (String) tableKategoriSampah.getValueAt(selectedRow, 1);
            int currentPoin = (int) tableKategoriSampah.getValueAt(selectedRow, 2);

            JTextField txtNamaKategori = new JTextField(currentNamaKategori);
            JTextField txtPoin = new JTextField(String.valueOf(currentPoin));

            Object[] message = {
                    "Nama Kategori:", txtNamaKategori,
                    "Poin:", txtPoin
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ubah Kategori Sampah",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String namaKategori = txtNamaKategori.getText();
                int poin;
                try {
                    poin = Integer.parseInt(txtPoin.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Poin harus angka.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                KategoriSampah kategori = new KategoriSampah(namaKategori, poin);
                kategori.setKategoriId(kategoriId);
                boolean success = kategoriSampahController.updateKategoriSampah(kategori);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Kategori sampah diubah.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadKategoriSampah();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengubah kategori sampah.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih kategori sampah terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Hapus Kategori Sampah
    private void hapusKategori() {
        int selectedRow = tableKategoriSampah.getSelectedRow();
        if (selectedRow >= 0) {
            int kategoriId = (int) tableKategoriSampah.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kategori sampah ini?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = kategoriSampahController.deleteKategoriSampah(kategoriId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Kategori sampah dihapus.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadKategoriSampah();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus kategori sampah.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih kategori sampah terlebih dahulu.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
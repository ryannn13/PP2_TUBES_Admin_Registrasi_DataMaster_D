package controller;

import dao.PenjemputanDAO;
import model.Penjemputan;
import java.util.Date;
import java.util.List;

public class PenjemputanController {
    private PenjemputanDAO penjemputanDAO;

    public PenjemputanController() {
        penjemputanDAO = new PenjemputanDAO();
    }

    // Create Penjemputan
    public boolean createPenjemputan(Penjemputan penjemputan) {
        // Validate
        if (penjemputan.getMasyarakatId() <= 0 || penjemputan.getSampahId() <= 0) {
            return false;
        }
        return penjemputanDAO.createPenjemputan(penjemputan);
    }

    // Get Penjemputan by status
    public List<Penjemputan> getPenjemputanByStatus(String status) {
        return penjemputanDAO.getPenjemputanByStatus(status);
    }

    // Update Penjemputan status
    public boolean updatePenjemputanStatus(int penjemputanId, String status, Integer kurirId, Integer dropboxId,
            Date tanggalSelesai) {
        if (penjemputanId <= 0 || status == null || status.isEmpty()) {
            return false; // Invalid input
        }
        // Ensure tanggalSelesai is java.sql.Date
        java.sql.Date sqlTanggalSelesai = null;
        if (tanggalSelesai != null) {
            sqlTanggalSelesai = new java.sql.Date(tanggalSelesai.getTime());
        }
        return penjemputanDAO.updateStatus(penjemputanId, status, kurirId, dropboxId, sqlTanggalSelesai);
    }

    // Assign kurir
    public boolean assignKurir(int penjemputanId, int kurirId) {
        return penjemputanDAO.assignKurir(penjemputanId, kurirId);
    }

    // Get Penjemputan by kurir and status
    public List<Penjemputan> getPenjemputanByKurir(int kurirId, String status) {
        return penjemputanDAO.getPenjemputanByKurir(kurirId, status);
    }

    // **Tambah Metode getAllPenjemputan**
    public List<Penjemputan> getAllPenjemputan() {
        return penjemputanDAO.getAllPenjemputan();
    }
}

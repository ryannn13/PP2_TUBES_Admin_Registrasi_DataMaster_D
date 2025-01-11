package controller;

import dao.KategoriSampahDAO;
import model.KategoriSampah;
import java.util.List;

public class KategoriSampahController {
    private KategoriSampahDAO kategoriSampahDAO;

    public KategoriSampahController() {
        kategoriSampahDAO = new KategoriSampahDAO();
    }

    // Create KategoriSampah
    public boolean createKategoriSampah(KategoriSampah kategori) {
        if (validateKategoriSampah(kategori)) {
            return kategoriSampahDAO.createKategoriSampah(kategori);
        }
        return false;
    }

    // Get All KategoriSampah
    public List<KategoriSampah> getAllKategoriSampah() {
        return kategoriSampahDAO.getAllKategoriSampah();
    }

    // Update KategoriSampah
    public boolean updateKategoriSampah(KategoriSampah kategori) {
        if (validateKategoriSampah(kategori)) {
            return kategoriSampahDAO.updateKategoriSampah(kategori);
        }
        return false;
    }

    // Delete KategoriSampah
    public boolean deleteKategoriSampah(int kategoriId) {
        return kategoriSampahDAO.deleteKategoriSampah(kategoriId);
    }

    // Get KategoriSampah by ID
    public KategoriSampah getKategoriSampahById(int kategoriId) {
        return kategoriSampahDAO.getKategoriSampahById(kategoriId);
    }

    // Validate KategoriSampah
    private boolean validateKategoriSampah(KategoriSampah kategori) {
        if (kategori.getNamaKategori() == null || kategori.getNamaKategori().isEmpty())
            return false;
        if (kategori.getPoin() <= 0)
            return false;
        return true;
    }
}

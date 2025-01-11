package controller;

import dao.SampahDAO;
import model.Sampah;
import java.util.List;

public class SampahController {
    private SampahDAO sampahDAO;

    public SampahController() {
        sampahDAO = new SampahDAO();
    }

    // Create Sampah
    public boolean createSampah(Sampah sampah) {
        if (validateSampah(sampah)) {
            return sampahDAO.createSampah(sampah);
        }
        return false;
    }

    // Get All Sampah
    public List<Sampah> getAllSampah() {
        return sampahDAO.getAllSampah();
    }

    // Update Sampah
    public boolean updateSampah(Sampah sampah) {
        if (validateSampah(sampah)) {
            return sampahDAO.updateSampah(sampah);
        }
        return false;
    }

    // Delete Sampah
    public boolean deleteSampah(int sampahId) {
        return sampahDAO.deleteSampah(sampahId);
    }

    // Validate Sampah
    private boolean validateSampah(Sampah sampah) {
        if (sampah.getNamaSampah() == null || sampah.getNamaSampah().isEmpty())
            return false;
        if (sampah.getJumlahSampah() <= 0)
            return false;
        if (sampah.getBeratSampah() <= 0)
            return false;
        if (sampah.getKategoriId() <= 0)
            return false;
        return true;
    }
}

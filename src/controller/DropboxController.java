package controller;

import dao.DropboxDAO;
import model.Dropbox;
import java.util.List;

public class DropboxController {
    private DropboxDAO dropboxDAO;

    public DropboxController() {
        dropboxDAO = new DropboxDAO();
    }

    // Create Dropbox
    public boolean createDropbox(Dropbox dropbox) {
        if (validateDropbox(dropbox)) {
            return dropboxDAO.createDropbox(dropbox);
        }
        return false;
    }

    // Get All Dropbox
    public List<Dropbox> getAllDropbox() {
        return dropboxDAO.getAllDropbox();
    }

    // Update Dropbox
    public boolean updateDropbox(Dropbox dropbox) {
        if (validateDropbox(dropbox)) {
            return dropboxDAO.updateDropbox(dropbox);
        }
        return false;
    }

    // Delete Dropbox
    public boolean deleteDropbox(int dropboxId) {
        return dropboxDAO.deleteDropbox(dropboxId);
    }

    // Validate Dropbox
    private boolean validateDropbox(Dropbox dropbox) {
        if (dropbox.getLokasi() == null || dropbox.getLokasi().isEmpty())
            return false;
        if (dropbox.getKapasitas() <= 0)
            return false;
        return true;
    }
}

package dao;

import model.Dropbox;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DropboxDAO {

    public boolean createDropbox(Dropbox dropbox) {
        String sql = "INSERT INTO Dropbox (lokasi, kapasitas) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dropbox.getLokasi());
            pstmt.setInt(2, dropbox.getKapasitas());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all Dropbox
    public List<Dropbox> getAllDropbox() {
        List<Dropbox> dropboxes = new ArrayList<>();
        String sql = "SELECT * FROM Dropbox";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dropbox dropbox = new Dropbox();
                dropbox.setDropboxId(rs.getInt("dropbox_id"));
                dropbox.setLokasi(rs.getString("lokasi"));
                dropbox.setKapasitas(rs.getInt("kapasitas"));
                dropboxes.add(dropbox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dropboxes;
    }

    // Update Dropbox
    public boolean updateDropbox(Dropbox dropbox) {
        String sql = "UPDATE Dropbox SET lokasi = ?, kapasitas = ? WHERE dropbox_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dropbox.getLokasi());
            pstmt.setInt(2, dropbox.getKapasitas());
            pstmt.setInt(3, dropbox.getDropboxId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Dropbox
    public boolean deleteDropbox(int dropboxId) {
        String sql = "DELETE FROM Dropbox WHERE dropbox_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dropboxId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Dropbox by ID
    public Dropbox getDropboxById(int dropboxId) {
        String sql = "SELECT * FROM Dropbox WHERE dropbox_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dropboxId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Dropbox dropbox = new Dropbox();
                dropbox.setDropboxId(rs.getInt("dropbox_id"));
                dropbox.setLokasi(rs.getString("lokasi"));
                dropbox.setKapasitas(rs.getInt("kapasitas"));
                return dropbox;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

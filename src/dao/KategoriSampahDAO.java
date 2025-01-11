package dao;

import model.KategoriSampah;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriSampahDAO {

    // Create new KategoriSampah
    public boolean createKategoriSampah(KategoriSampah kategori) {
        String sql = "INSERT INTO kategorisampah (nama_kategori, poin) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kategori.getNamaKategori());
            pstmt.setInt(2, kategori.getPoin());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all KategoriSampah
    public List<KategoriSampah> getAllKategoriSampah() {
        List<KategoriSampah> kategoris = new ArrayList<>();
        String sql = "SELECT * FROM kategorisampah";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                KategoriSampah kategori = new KategoriSampah();
                kategori.setKategoriId(rs.getInt("kategori_id"));
                kategori.setNamaKategori(rs.getString("nama_kategori"));
                kategori.setPoin(rs.getInt("poin"));
                kategoris.add(kategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategoris;
    }

    // Update KategoriSampah
    public boolean updateKategoriSampah(KategoriSampah kategori) {
        String sql = "UPDATE kategorisampah SET nama_kategori = ?, poin = ? WHERE kategori_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kategori.getNamaKategori());
            pstmt.setInt(2, kategori.getPoin());
            pstmt.setInt(3, kategori.getKategoriId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete KategoriSampah
    public boolean deleteKategoriSampah(int kategoriId) {
        String sql = "DELETE FROM kategorisampah WHERE kategori_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kategoriId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get KategoriSampah by ID
    public KategoriSampah getKategoriSampahById(int kategoriId) {
        String sql = "SELECT * FROM kategorisampah WHERE kategori_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kategoriId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                KategoriSampah kategori = new KategoriSampah();
                kategori.setKategoriId(rs.getInt("kategori_id"));
                kategori.setNamaKategori(rs.getString("nama_kategori"));
                kategori.setPoin(rs.getInt("poin"));
                return kategori;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

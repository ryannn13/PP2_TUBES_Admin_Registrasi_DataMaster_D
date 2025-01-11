package dao;

import model.Sampah;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SampahDAO {

    // Create new Sampah
    public boolean createSampah(Sampah sampah) {
        String sql = "INSERT INTO sampah (nama_sampah, jumlah_sampah, berat_sampah, kategori_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sampah.getNamaSampah());
            pstmt.setInt(2, sampah.getJumlahSampah());
            pstmt.setDouble(3, sampah.getBeratSampah());
            pstmt.setInt(4, sampah.getKategoriId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all Sampah
    public List<Sampah> getAllSampah() {
        List<Sampah> sampahs = new ArrayList<>();
        String sql = "SELECT * FROM sampah";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Sampah sampah = new Sampah();
                sampah.setSampahId(rs.getInt("sampah_id"));
                sampah.setNamaSampah(rs.getString("nama_sampah"));
                sampah.setJumlahSampah(rs.getInt("jumlah_sampah"));
                sampah.setBeratSampah(rs.getDouble("berat_sampah"));
                sampah.setKategoriId(rs.getInt("kategori_id"));
                sampahs.add(sampah);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sampahs;
    }

    // Update Sampah
    public boolean updateSampah(Sampah sampah) {
        String sql = "UPDATE sampah SET nama_sampah = ?, jumlah_sampah = ?, berat_sampah = ?, kategori_id = ? WHERE sampah_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sampah.getNamaSampah());
            pstmt.setInt(2, sampah.getJumlahSampah());
            pstmt.setDouble(3, sampah.getBeratSampah());
            pstmt.setInt(4, sampah.getKategoriId());
            pstmt.setInt(5, sampah.getSampahId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Sampah
    public boolean deleteSampah(int sampahId) {
        String sql = "DELETE FROM sampah WHERE sampah_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sampahId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Sampah by ID
    public Sampah getSampahById(int sampahId) {
        String sql = "SELECT * FROM sampah WHERE sampah_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sampahId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Sampah sampah = new Sampah();
                sampah.setSampahId(rs.getInt("sampah_id"));
                sampah.setNamaSampah(rs.getString("nama_sampah"));
                sampah.setJumlahSampah(rs.getInt("jumlah_sampah"));
                sampah.setBeratSampah(rs.getDouble("berat_sampah"));
                sampah.setKategoriId(rs.getInt("kategori_id"));
                return sampah;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package dao;

import model.Penjemputan;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PenjemputanDAO {

    // Create new Penjemputan
    public boolean createPenjemputan(Penjemputan penjemputan) {
        String sql = "INSERT INTO Penjemputan (masyarakat_id, sampah_id, status, tanggal_request) VALUES (?, ?, 'pending', NOW())";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, penjemputan.getMasyarakatId());
            pstmt.setInt(2, penjemputan.getSampahId());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all Penjemputan by status
    public List<Penjemputan> getPenjemputanByStatus(String status) {
        List<Penjemputan> penjemputans = new ArrayList<>();
        String sql = "SELECT * FROM Penjemputan WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Penjemputan penjemputan = new Penjemputan();
                penjemputan.setPenjemputanId(rs.getInt("penjemputan_id"));
                penjemputan.setMasyarakatId(rs.getInt("masyarakat_id"));
                penjemputan.setKurirId(rs.getInt("kurir_id"));
                penjemputan.setSampahId(rs.getInt("sampah_id"));
                penjemputan.setDropboxId(rs.getInt("dropbox_id"));
                penjemputan.setStatus(rs.getString("status"));
                penjemputan.setTanggalRequest(rs.getTimestamp("tanggal_request"));
                penjemputan.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                penjemputans.add(penjemputan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penjemputans;
    }

    // Update status
    public boolean updateStatus(int penjemputanId, String status, Integer kurirId, Integer dropboxId,
            Date tanggalSelesai) {
        String sql = "UPDATE penjemputan SET status = ?, kurir_id = ?, dropbox_id = ?, tanggal_selesai = ? WHERE penjemputan_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            if (kurirId != null) {
                pstmt.setInt(2, kurirId);
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            if (dropboxId != null) {
                pstmt.setInt(3, dropboxId);
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            if (tanggalSelesai != null) {
                pstmt.setDate(4, new java.sql.Date(tanggalSelesai.getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }
            pstmt.setInt(5, penjemputanId);

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Assign kurir to penjemputan
    public boolean assignKurir(int penjemputanId, int kurirId) {
        String sql = "UPDATE Penjemputan SET kurir_id = ?, status = 'dalam penjemputan' WHERE penjemputan_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kurirId);
            pstmt.setInt(2, penjemputanId);

            int affected = pstmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get penjemputan by kurir and status
    public List<Penjemputan> getPenjemputanByKurir(int kurirId, String status) {
        List<Penjemputan> penjemputans = new ArrayList<>();
        String sql = "SELECT * FROM Penjemputan WHERE kurir_id = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kurirId);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Penjemputan penjemputan = new Penjemputan();
                penjemputan.setPenjemputanId(rs.getInt("penjemputan_id"));
                penjemputan.setMasyarakatId(rs.getInt("masyarakat_id"));
                penjemputan.setKurirId(rs.getInt("kurir_id"));
                penjemputan.setSampahId(rs.getInt("sampah_id"));
                penjemputan.setDropboxId(rs.getInt("dropbox_id"));
                penjemputan.setStatus(rs.getString("status"));
                penjemputan.setTanggalRequest(rs.getTimestamp("tanggal_request"));
                penjemputan.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                penjemputans.add(penjemputan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penjemputans;
    }

    // **Tambah Metode getAllPenjemputan**
    public List<Penjemputan> getAllPenjemputan() {
        List<Penjemputan> penjemputans = new ArrayList<>();
        String sql = "SELECT * FROM Penjemputan";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Penjemputan penjemputan = new Penjemputan();
                penjemputan.setPenjemputanId(rs.getInt("penjemputan_id"));
                penjemputan.setMasyarakatId(rs.getInt("masyarakat_id"));
                penjemputan.setKurirId(rs.getInt("kurir_id"));
                penjemputan.setSampahId(rs.getInt("sampah_id"));
                penjemputan.setDropboxId(rs.getInt("dropbox_id"));
                penjemputan.setStatus(rs.getString("status"));
                penjemputan.setTanggalRequest(rs.getTimestamp("tanggal_request"));
                penjemputan.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                penjemputans.add(penjemputan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penjemputans;
    }
}

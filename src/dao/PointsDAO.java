package dao;

import model.Points;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointsDAO {

    // Add new points
    public boolean addPoints(Points points) {
        String sql = "INSERT INTO Points (masyarakat_id, jumlah) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, points.getMasyarakatId());
            pstmt.setInt(2, points.getJumlah());

            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get total points for a user
    public int getTotalPoints(int masyarakatId) {
        String sql = "SELECT SUM(jumlah) as total_poin FROM Points WHERE masyarakat_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, masyarakatId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_poin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get points history
    public List<Points> getPointsHistory(int masyarakatId) {
        List<Points> pointsList = new ArrayList<>();
        String sql = "SELECT * FROM Points WHERE masyarakat_id = ? ORDER BY tanggal DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, masyarakatId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Points points = new Points();
                points.setPointId(rs.getInt("point_id"));
                points.setMasyarakatId(rs.getInt("masyarakat_id"));
                points.setJumlah(rs.getInt("jumlah"));
                points.setTanggal(rs.getTimestamp("tanggal"));
                pointsList.add(points);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pointsList;
    }
}

package controller;

import dao.PointsDAO;
import model.Points;
import java.util.List;

public class PointsController {
    private PointsDAO pointsDAO;

    public PointsController() {
        pointsDAO = new PointsDAO();
    }

    // Add new points
    public boolean addPoints(Points points) {
        if (validatePoints(points)) {
            return pointsDAO.addPoints(points);
        }
        return false;
    }

    // Get total points for a user
    public int getTotalPoints(int masyarakatId) {
        return pointsDAO.getTotalPoints(masyarakatId);
    }

    // Get points history
    public List<Points> getPointsHistory(int masyarakatId) {
        return pointsDAO.getPointsHistory(masyarakatId);
    }

    // Validate Points
    private boolean validatePoints(Points points) {
        if (points.getMasyarakatId() <= 0)
            return false;
        if (points.getJumlah() <= 0)
            return false;
        return true;
    }
}

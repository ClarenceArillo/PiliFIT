package com.example.pilifitproject.dao;

import com.example.pilifitproject.model.Fit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FitDAO extends BaseDAO<Fit>{

    @Override
    public void add(Fit fit) throws SQLException {
        String sql = "INSERT INTO fit (name, top_id, bottom_id, shoes_id, is_favorite) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); // Using inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, fit.getName());
            pstmt.setInt(2, fit.getTopId());
            pstmt.setInt(3, fit.getBottomId());
            pstmt.setInt(4, fit.getShoesId());
            pstmt.setInt(5, fit.getIs_Favorite());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fit.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM fit WHERE id = ?";

        try (Connection conn = getConnection(); // Using inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Keep all existing methods with minimal changes
    public void updateFit(Fit fit) throws SQLException {
        String sql = "UPDATE fit SET name = ? WHERE id = ?";

        try (Connection conn = getConnection(); // Changed to inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fit.getName());
            pstmt.setInt(2, fit.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Fit> getAllFits() throws SQLException {
        List<Fit> fits = new ArrayList<>();
        String sql = "SELECT * FROM fit";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                fits.add(new Fit(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("top_id"),
                        rs.getInt("bottom_id"),
                        rs.getInt("shoes_id"),
                        rs.getInt("is_favorite")
                ));
            }
        }
        return fits;
    }

}

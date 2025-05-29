package com.example.pilifitproject.dao;

import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.model.Fit;
import com.example.pilifitproject.utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FitDAO {

    //add fit method
    public void addFit(Fit fit)throws SQLException {
        String sql = "INSERT INTO fit (name, top_id, bottom_id, shoes_id, is_favorite)" + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

             pstmt.setString (1, fit.getName());
             pstmt.setInt(2, fit.getTop().getId());
             pstmt.setInt(3, fit.getBottom().getId());
             pstmt.setInt(4, fit.getShoes().getId());
             pstmt.setInt(5, fit.getIs_Favorite());
             pstmt.executeUpdate();

             try (ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                 if (generatedKeys.next()){
                     fit.setId(generatedKeys.getInt(1));
                 }
             }
        }

    }

    //deletefit method
    public void deleteFit(int FitID)throws SQLException {
        String sql = "DELETE FROM fit WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setInt(1,FitID);
            pstmt.executeUpdate();

        }

    }

    public List<Fit> getAllFits()throws SQLException{
        List<Fit> fits = new ArrayList<>();
        String sql = "SELECT f.*, " +
                     "t.name as top_name, t.image_path as top_image, " +
                     "b.name as bottom_name, b.image_path as bottom_image, " +
                     "s.name as shoes_name, s.image_path as shoes_image " +
                     "FROM fit f "+
                     "LEFT JOIN clothing_item t ON f.top_id = t.id " +
                     "LEFT JOIN clothing item b ON f.bottom_id = b.id " +
                     "LEFT JOIN clothing item s ON f.shoes_id = s.id ";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Fit fit = new Fit(
                        rs.getInt("id"),
                        rs.getString("name"),

                        new ClothingItem(rs.getInt("top_id"), rs.getString("top_name"),
                                rs.getString("top_image"), 0, 0, 0, "", 0),

                        new ClothingItem(rs.getInt("bottom_id"), rs.getString("bottom_name"),
                                rs.getString("top_image"), 0, 0, 0, "", 0),

                        new ClothingItem(rs.getInt("accessories_id"), rs.getString("accessories_name"),
                                rs.getString("accessories_image"), 0, 0, 0, "", 0),

                        rs.getInt("is_favorite")
                );
                        fits.add(fit);
            }
        }


        return fits;
    }


    //add and remove fit from favorites
    //add to favorite fit
    public void addFitToFavorite(int FitID)throws SQLException {
        String sql = "UPDATE fit SET is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Constants.FAVORITE);
            pstmt.setInt(2,FitID);
            pstmt.executeUpdate();

        }

    }

    //remove from favorite fit
    public void removeFitFromFavorite(int FitID)throws SQLException {
        String sql = "UPDATE fit SET is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Constants.NOT_FAVORITE);
            pstmt.setInt(2,FitID);
            pstmt.executeUpdate();

        }

    }




}

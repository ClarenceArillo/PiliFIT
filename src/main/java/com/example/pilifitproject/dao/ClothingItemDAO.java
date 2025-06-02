package com.example.pilifitproject.dao;

import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.Constants;
import org.sqlite.core.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemDAO {
    public List<ClothingItem> getAllClothingItem() throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClothingItem item = new ClothingItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image_path"),
                        rs.getInt("category_id"),
                        rs.getInt("color_id"),
                        rs.getInt("style_id"),
                        rs.getString("size"),
                        rs.getInt("is_favorite")
                );
                items.add(item);
            }
        }
        return items;
    }

    public void addClothingItem(ClothingItem item) throws SQLException {
        String sql = "INSERT INTO clothing_item (name, image_path, category_id, color_id, style_id, size, is_favorite)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getImagePath());
            pstmt.setInt(3, item.getCategoryId());
            pstmt.setInt(4, item.getColorId());
            pstmt.setInt(5, item.getStyleId());
            pstmt.setString(6, item.getSize());
            pstmt.setInt(7, item.getIsFavorite());
            pstmt.executeUpdate();

        }
    }

    public void updateClothingItem(ClothingItem item) throws SQLException {
        String sql = "UPDATE clothing_item SET name = ?, category_id = ?, color_id = ?, " +
                "style_id = ?, size = ?, is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getCategoryId());
            pstmt.setInt(3, item.getColorId());
            pstmt.setInt(4, item.getStyleId());
            pstmt.setString(5, item.getSize());
            pstmt.setInt(6, item.getIsFavorite());
            pstmt.setInt(7, item.getId());
            pstmt.executeUpdate();

        }

    }

    public void deleteClothingItem(int id) throws SQLException {
        String sql = "DELETE FROM clothing_item WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    //add and remove clothing item from favorites
    //add to favorite fit
    public void addClothingItemToFavorite(int FitID)throws SQLException {
        String sql = "UPDATE fit SET is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Constants.FAVORITE);
            pstmt.setInt(2,FitID);
            pstmt.executeUpdate();

        }

    }

    //remove from favorite fit
    public void removeClothingItemFromFavorite(int FitID)throws SQLException {
        String sql = "UPDATE fit SET is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Constants.NOT_FAVORITE);
            pstmt.setInt(2,FitID);
            pstmt.executeUpdate();

        }

    }

    //this method returns category id
    public ClothingItem getClothingItemById(int id) throws SQLException {
        String sql = "SELECT * FROM clothing_item WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ClothingItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image_path"),
                        rs.getInt("category_id"),
                        rs.getInt("color_id"),
                        rs.getInt("style_id"),
                        rs.getString("size"),
                        rs.getInt("is_favorite")
                );
            }
        }

        return null;
    }

    //Filter by Category
    public static List<ClothingItem> getAllClothingItemsByCategory(int categoryId) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("image_path"),
                            rs.getInt("category_id"),
                            rs.getInt("color_id"),
                            rs.getInt("style_id"),
                            rs.getString("size"),
                            rs.getInt("is_Favorite")
                    );
                    items.add(item);
                }
            }

            return items;
        }


    }

    //    Colors:
//    Red
//    Orange
//    Yellow
//    Green
//    Violet
//    Blue
//    White
//    Black
//    Others

    //Color Filter
    public static List<ClothingItem> getAllClothingItemsByColor(int colorId) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE color_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, colorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("image_path"),
                            rs.getInt("category_id"),
                            rs.getInt("color_id"),
                            rs.getInt("style_id"),
                            rs.getString("size"),
                            rs.getInt("is_Favorite")
                    );
                    items.add(item);
                }
            }

            return items;
        }


    }


//Style
//    Formal
//    Casual
//    Semi-Formal
//    Others

    //Style Filter
    public static List<ClothingItem> getAllClothingItemsByStyleId(int styleId) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE style_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, styleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("image_path"),
                            rs.getInt("category_id"),
                            rs.getInt("color_id"),
                            rs.getInt("style_id"),
                            rs.getString("size"),
                            rs.getInt("is_Favorite")
                    );
                    items.add(item);
                }
            }

            return items;
        }


    }

    // favorite filter
    public static List<ClothingItem> getAllClothingItemsByIsFavorite(int is_favorite) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE is_favorite = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, is_favorite);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("image_path"),
                            rs.getInt("category_id"),
                            rs.getInt("color_id"),
                            rs.getInt("style_id"),
                            rs.getString("size"),
                            rs.getInt("is_favorite")
                    );
                    items.add(item);
                }
            }

            return items;
        }


    }

}

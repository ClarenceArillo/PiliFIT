package com.example.pilifitproject.dao;

import com.example.pilifitproject.model.ClothingItem;
import org.sqlite.core.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemDAO {
    public List<ClothingItem> getAllClothingItem() throws SQLException{
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                ClothingItem item = new ClothingItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image_path"),
                        rs.getInt("category_id"),
                        rs.getString("color"),
                        rs.getString("style"),
                        rs.getString("size"),
                        rs.getInt("is_favorite")
                );
                items.add(item);
            }
        }
        return items;
    }

    public void addClothingItem(ClothingItem item) throws SQLException{
        String sql = "INSERT INTO clothing_item (name, image_path, category_id, color, style, size, is_favorite)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getImagePath());
            pstmt.setInt(3, item.getCategoryId());
            pstmt.setString(4, item.getColor());
            pstmt.setString(5, item.getStyle());
            pstmt.setString(6, item.getSize());
            pstmt.setInt(7, item.getIsFavorite());
            pstmt.executeUpdate();

        }
    }

    public void updateClothingItem(ClothingItem item) throws SQLException {
        String sql = "UPDATE clothing_item SET name = ?, category_id = ?, color = ?, " +
                "style = ?, size = ?, is_favorite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

             pstmt.setString(1, item.getName());
             pstmt.setInt(2, item.getCategoryId());
             pstmt.setString(3, item.getColor());
             pstmt.setString(4, item.getStyle());
             pstmt.setString(5, item.getSize());
             pstmt.setInt(6, item.getIsFavorite());
             pstmt.setInt(7, item.getId());
             pstmt.executeUpdate();

        }

    }

    public void deleteClothingItem(int id) throws SQLException{
        String sql = "DELETE FROM clothing_item WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))  {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

//    public List<ClothingItem> getAllClothingItemsByCategory(int categoryId)throws SQLException{
//        List<ClothingItem> items = new ArrayList<>();
//
//
//    }


}

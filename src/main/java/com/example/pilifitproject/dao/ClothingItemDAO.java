package com.example.pilifitproject.dao;

import com.example.pilifitproject.model.ClothingItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemDAO extends BaseDAO<ClothingItem> {

    public List<ClothingItem> getAllClothingItem() throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClothingItem item = new ClothingItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBytes("image_data"),
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

    @Override
    public void add(ClothingItem item) throws SQLException {
        String sql = "INSERT INTO clothing_item (name, image_data, category_id, color_id, style_id, size, is_favorite) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); // Using inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setClothingItemParameters(pstmt, item);
            pstmt.executeUpdate();
        }
    }


    public void updateClothingItem(ClothingItem item) throws SQLException {
        String sql = "UPDATE clothing_item SET name = ?, category_id = ?, color_id = ?, style_id = ?, size = ? WHERE id = ?";

        try (Connection conn = getConnection(); // Changed to inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getCategoryId());
            pstmt.setInt(3, item.getColorId());
            pstmt.setInt(4, item.getStyleId());
            pstmt.setString(5, item.getSize());
            pstmt.setInt(6, item.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM clothing_item WHERE id = ?";

        try (Connection conn = getConnection(); // Using inherited method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private ClothingItem createClothingItemFromResultSet(ResultSet rs) throws SQLException {
        return new ClothingItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBytes("image_data"),
                rs.getInt("category_id"),
                rs.getInt("color_id"),
                rs.getInt("style_id"),
                rs.getString("size"),
                rs.getInt("is_favorite")
        );
    }

    private void setClothingItemParameters(PreparedStatement pstmt, ClothingItem item) throws SQLException {
        pstmt.setString(1, item.getName());
        pstmt.setBytes(2, item.getImageData());
        pstmt.setInt(3, item.getCategoryId());
        pstmt.setInt(4, item.getColorId());
        pstmt.setInt(5, item.getStyleId());
        pstmt.setString(6, item.getSize());
        pstmt.setInt(7, item.getIsFavorite());
    }

    //add and remove clothing item from favorites
    //add to favorite fit
    public void addClothingItemToFavorite(int itemID, int favoriteStatus)throws SQLException {
        String sql = "UPDATE Clothing_item SET is_favorite = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, favoriteStatus);
            pstmt.setInt(2,itemID);
            pstmt.executeUpdate();
        }
    }

    //this method returns category id
    public ClothingItem getClothingItemById(int id) throws SQLException {
        String sql = "SELECT * FROM clothing_item WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ClothingItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBytes("image_data"),
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
    public List<ClothingItem> getAllClothingItemsByCategory(int categoryId) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE category_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBytes("image_data"),
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
    public List<ClothingItem> getAllClothingItemsByIsFavorite(int is_favorite) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_item WHERE is_favorite = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, is_favorite);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBytes("image_data"),
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

    public List<ClothingItem> getFilteredClothingItems(
            Integer categoryId,
            Integer colorId,
            Integer styleId
    ) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM clothing_item WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        if (colorId != null) {
            sql.append(" AND color_id = ?");
            params.add(colorId);
        }

        if (styleId != null) {
            sql.append(" AND style_id = ?");
            params.add(styleId);
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ClothingItem item = new ClothingItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBytes("image_data"),
                            rs.getInt("category_id"),
                            rs.getInt("color_id"),
                            rs.getInt("style_id"),
                            rs.getString("size"),
                            rs.getInt("is_favorite")
                    );
                    items.add(item);
                }
            }
        }

        return items;
    }

}

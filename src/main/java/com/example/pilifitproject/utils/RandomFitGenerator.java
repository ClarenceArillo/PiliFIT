package com.example.pilifitproject.utils;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import java.sql.SQLException;
import java.util.List;

public class RandomFitGenerator {
    private final ClothingItemDAO clothingItemDAO = new ClothingItemDAO();

    public GeneratedFitPreview generateRandomPreview() throws SQLException {
        ClothingItem top = getRandomItemByCategory(1); // 1 = top
        ClothingItem bottom = getRandomItemByCategory(2); // 2 = bottom
        ClothingItem shoes = getRandomItemByCategory(3); // 3 = shoes

        return new GeneratedFitPreview(top, bottom, shoes);
    }

    private ClothingItem getRandomItemByCategory(int categoryId) throws SQLException {
        List<ClothingItem> items = clothingItemDAO.getAllClothingItemsByCategory(categoryId);
        if (items.isEmpty()) {
            throw new SQLException("No items found for category: " + categoryId);
        }
        return items.get((int) (Math.random() * items.size()));
    }

}








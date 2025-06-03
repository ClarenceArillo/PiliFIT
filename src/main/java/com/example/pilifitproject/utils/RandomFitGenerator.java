package com.example.pilifitproject.utils;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.model.Fit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomFitGenerator {

    private final ClothingItemDAO clothingItemDAO;
    private final Random random;

    public RandomFitGenerator() {
        this.clothingItemDAO = new ClothingItemDAO();
        this.random = new Random();
    }

    /**
     * Generates a random Fit using category IDs:
     * 1 = Top, 2 = Bottom, 3 = Shoes
     *
     * @param fitName The name of the generated fit.
     * @return A new Fit object (not yet saved to the database).
     * @throws SQLException If retrieval from database fails.
     */



    private List<ClothingItem> filterByCategory(List<ClothingItem> items, String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static List<ClothingItem> generateRandomFit() {
        List<ClothingItem> fit = new ArrayList<>();
        ClothingItemDAO dao = new ClothingItemDAO();

        try {
            // Assuming category IDs: 1 = Top, 2 = Bottom, 3 = Shoes
            ClothingItem top = dao.getRandomClothingItemByCategory(1);
            ClothingItem bottom = dao.getRandomClothingItemByCategory(2);
            ClothingItem shoes = dao.getRandomClothingItemByCategory(3);

            if (top != null) fit.add(top);
            if (bottom != null) fit.add(bottom);
            if (shoes != null) fit.add(shoes);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fit;
    }



    private ClothingItem getRandomItem(List<ClothingItem> items) {
        int index = random.nextInt(items.size());
        return items.get(index);
    }
}
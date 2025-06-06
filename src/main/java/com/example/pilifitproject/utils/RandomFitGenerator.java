package com.example.pilifitproject.utils;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import java.sql.SQLException;

public class RandomFitGenerator {
    private final ClothingItemDAO dao;

    public RandomFitGenerator() {
        this.dao = new ClothingItemDAO();
    }

       // ======= NOTE: When user click "Generate" this method will be called=======

    public GeneratedFitPreview generateRandomPreview() throws SQLException {
        ClothingItem top = dao.getRandomClothingItemByCategory(1);
        ClothingItem bottom = dao.getRandomClothingItemByCategory(2);
        ClothingItem shoes = dao.getRandomClothingItemByCategory(3);

        if (top == null || bottom == null || shoes == null) {
            throw new IllegalStateException("One or more categories are empty.");
        }

        return new GeneratedFitPreview(top, bottom, shoes);
    }



}








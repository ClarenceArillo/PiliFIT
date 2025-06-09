package com.example.pilifitproject;

public interface RefreshableController {
    void refreshClothingItems();
    void refreshFavorites();
    void applyFilters(Integer categoryId, Integer colorId, Integer styleId);
}

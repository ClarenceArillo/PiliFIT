    package com.example.pilifitproject;

    public interface RefreshableController {
        void refreshClothingItems();
        void refreshFavorites();

        default void refreshAll() {
            refreshClothingItems();
            refreshFavorites();
        }
    }

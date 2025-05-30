package com.example.pilifitproject;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.DBConnection;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sqlite.SQLiteException;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/pilifitproject/view/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        DBConnection.testConnection();

        ClothingItemDAO dao = new ClothingItemDAO();
        ClothingItem item = new ClothingItem(
                9,
                "Black Heels",
                "/images/footwear/F2.png",
                3,
                9,
                3,
                "8",
                0
        );

        try {
            /* ========== TEST 1: BASIC CRUD OPERATIONS ========== */

            // Add item
            dao.addClothingItem(item);
            System.out.println("Item added successfully");

            // Update item
//            dao.updateClothingItem(item);
//            System.out.println("Item updated successfully");

            // Delete item
//            dao.deleteClothingItem(4);
//            dao.deleteClothingItem(5);
//            System.out.println("Items deleted successfully");

            // Get all items
            for(ClothingItem c: dao.getAllClothingItem()){
                System.out.println( "ID          : " + c.getId() +
                                    "\nName       : " + c.getName() +
                                    "\nImagePath  : " + c.getImagePath() +
                                    "\nCategoryID : " + c.getCategoryId() +
                                    "\nColorID    : " + c.getColorId() +
                                    "\nStyleID    : " + c.getStyleId() +
                                    "\nSize       : " + c.getSize() +
                                    "\nIsFavorite : " + c.getIsFavorite()
                );
            }
            System.out.println();


            /* ========== TEST 2: GET ITEMS BY CATEGORY ========== */
            /*
            int categoryIdTest = 1;
            System.out.println("Test categoryItems filter");

            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByCategory(categoryIdTest);

            if(items.isEmpty()){
                System.out.println("No items found");
            }else{
                System.out.println("Found: " + items.size() + " items");
                for(ClothingItem item : items){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());
                }
            }
            */

            /* ========== TEST 3: GET ITEMS BY COLOR ========== */
            /*
            int colorIdTest = 1;
            System.out.println("Test colorItems filter");

            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByColor(colorIdTest);

            if(items.isEmpty()){
                System.out.println("No items found");
            }else{
                System.out.println("Found: " + items.size() + " items");
                for(ClothingItem item : items){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());
                }
            }
            */

            /* ========== TEST 4: GET ITEMS BY STYLE ========== */
            /*
            int styleIDTest = 1;
            System.out.println("Test styleItems filter");

            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByStyleId(styleIDTest);

            if(items.isEmpty()){
                System.out.println("No items found");
            }else{
                System.out.println("Found: " + items.size() + " items");
                for(ClothingItem item : items){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());
                }
            }
            */

            /* ========== TEST 5: GET FAVORITE ITEMS ========== */
            /*
            System.out.println("Test ClothingItems Favorites Filter");
            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByIsFavorite(Constants.FAVORITE);

            if(items.isEmpty()){
                System.out.println("No favorite items found");
            }else{
                System.out.println("Found: " + items.size() + " favorite items");
                for(ClothingItem item : items){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());
                }
            }
            */

            /* ========== TEST 6: GET NON-FAVORITE ITEMS ========== */
            /*
            System.out.println("Test ClothingItems NotFavorites Filter");
            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByIsFavorite(Constants.NOT_FAVORITE);

            if(items.isEmpty()){
                System.out.println("No non-favorite items found");
            }else{
                System.out.println("Found: " + items.size() + " non-favorite items");
                for(ClothingItem item : items){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());
                }
            }
            */

        } catch (SQLiteException e) {
            System.out.println("Database operation failed");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //launch();


    }
}
package com.example.pilifitproject;

import com.example.pilifitproject.controller.HomeController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.DBConnection;
import com.example.pilifitproject.dao.FitDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.model.Fit;
import com.example.pilifitproject.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sqlite.SQLiteException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/pilifitproject/view/Home.fxml"));
        Parent root = fxmlLoader.load();

        //image display test
//        HomeController controller = fxmlLoader.getController();
//
//        ClothingItem testItem = new ClothingItem(
//                1,
//                "test item",
//                "/com/example/pilifitproject/images/top/T1.png",
//                1,3,3, "M", 0
//        );
//
//        List<ClothingItem> items = new ClothingItemDAO().getAllClothingItem();
//        if (!items.isEmpty()) {
//            // Fix the path by adding the full package path
//            items.get(0).setImagePath("/com/example/pilifitproject" + items.get(0).getImagePath());
//            controller.displayItem(items.get(0));
//        }
//
//        controller.displayItem(testItem);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        DBConnection.testConnection();

        ClothingItemDAO dao = new ClothingItemDAO();
        FitDAO fitDao = new FitDAO();

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
//            dao.addClothingItem(item);
//            System.out.println("Item added successfully");

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
                System.out.println();
            }



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

            /* ========== FIT DAO TESTING ========== */
            List<ClothingItem> allItems = dao.getAllClothingItem();
            if (allItems.isEmpty()) {
                System.out.println("No clothing items found - please add some first");
                return;
            }

            // Get sample items for top, bottom, shoes
            ClothingItem top = allItems.stream()
                    .filter(i -> i.getCategoryId() == 1) // Assuming 1 is tops
                    .findFirst()
                    .orElse(allItems.get(0));

            ClothingItem bottom = allItems.stream()
                    .filter(i -> i.getCategoryId() == 2) // Assuming 2 is bottoms
                    .findFirst()
                    .orElse(allItems.get(0));

            ClothingItem shoes = allItems.stream()
                    .filter(i -> i.getCategoryId() == 3) // Assuming 3 is footwear
                    .findFirst()
                    .orElse(allItems.get(0));

            /* ========== FIT DAO TESTING ========== */

            // TEST 1: Add a new fit
            System.out.println("\n=== TEST 1: Adding a new fit ===");
            Fit newFit = new Fit(0, "Casual Outfit", top, bottom, shoes, Constants.NOT_FAVORITE);
            fitDao.addFit(newFit);
            System.out.println("Added fit with ID: " + newFit.getId());

            // TEST 2: Get all fits
            System.out.println("\n=== TEST 2: Getting all fits ===");
            List<Fit> allFits = fitDao.getAllFits();
            if (allFits.isEmpty()) {
                System.out.println("No fits found");
            } else {
                System.out.println("Found " + allFits.size() + " fits:");
                for (Fit fit : allFits) {
                    System.out.println(
                            "ID: " + fit.getId() + "\n" +
                                    "Name: " + fit.getName() + "\n" +
                                    "Top: " + fit.getTop().getName() + "\n" +
                                    "Bottom: " + fit.getBottom().getName() + "\n" +
                                    "Shoes: " + (fit.getShoes() != null ? fit.getShoes().getName() : "None") + "\n" +
                                    "Favorite: " + fit.getIs_Favorite() + "\n"
                    );
                }
            }

            // TEST 3: Add fit to favorites

            if (!allFits.isEmpty()) {
                System.out.println("\n=== TEST 3: Adding fit to favorites ===");
                Fit fitToFavorite = allFits.get(0);
                System.out.println("Before favorite status: " + fitToFavorite.getIs_Favorite());
                fitDao.addFitToFavorite(fitToFavorite.getId());

                // Verify update
                Fit updatedFit = fitDao.getAllFits().stream()
                        .filter(f -> f.getId() == fitToFavorite.getId())
                        .findFirst()
                        .orElse(null);
                System.out.println("After favorite status: " +
                        (updatedFit != null ? updatedFit.getIs_Favorite() : "Not found"));
            }



            // TEST 4: Remove fit from favorites
            /*
            if (!allFits.isEmpty()) {
                System.out.println("\n=== TEST 4: Removing fit from favorites ===");
                Fit fitToUnfavorite = allFits.get(0);
                System.out.println("Before favorite status: " + fitToUnfavorite.getIs_Favorite());
                fitDao.removeFitFromFavorite(fitToUnfavorite.getId());

                // Verify update
                Fit updatedFit = fitDao.getAllFits().stream()
                        .filter(f -> f.getId() == fitToUnfavorite.getId())
                        .findFirst()
                        .orElse(null);
                System.out.println("After favorite status: " +
                        (updatedFit != null ? updatedFit.getIs_Favorite() : "Not found"));
            }

             */

            // TEST 5: Delete a fit
            /*
            if (!allFits.isEmpty()) {
                System.out.println("\n=== TEST 5: Deleting a fit ===");
                Fit fitToDelete = allFits.get(0);
                int beforeCount = fitDao.getAllFits().size();
                System.out.println("Fits before deletion: " + beforeCount);

                fitDao.deleteFit(fitToDelete.getId());

                int afterCount = fitDao.getAllFits().size();
                System.out.println("Fits after deletion: " + afterCount);
                System.out.println("Deletion " + (afterCount < beforeCount ? "successful" : "failed"));


             */



        } catch (SQLiteException e) {
            System.out.println("Database operation failed");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch();


    }
}
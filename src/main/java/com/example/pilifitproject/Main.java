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
import java.util.Optional;
import java.util.stream.Collectors;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/pilifitproject/view/Home.fxml"));
        Parent root = fxmlLoader.load();

        /* =============image display test  =========*/
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

        Scene scene = new Scene(root, 724, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        DBConnection.testConnection();

        ClothingItemDAO dao = new ClothingItemDAO();
        FitDAO fitDao = new FitDAO();

        ClothingItem item = new ClothingItem(
                6,
                "Black Leather Jacket",
                "/com/example/pilifitproject/images/top/T3.png",
                1,
                8,
                3,
                "XL",
                1
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
            List <ClothingItem> top = allItems.stream()
                    .filter(i -> i.getCategoryId() == 1) //  1 is tops
                    .collect(Collectors.toList());


            List <ClothingItem> bottom = allItems.stream()
                    .filter(i -> i.getCategoryId() == 2) //  2 is bottom
                    .collect(Collectors.toList());

            List <ClothingItem> shoes = allItems.stream()
                    .filter(i -> i.getCategoryId() == 3) //  3 is shoes
                    .collect(Collectors.toList());



            int topIndexID = 6;
            int bottomIndexID = 8;
            int shoeIndexID = 9;

            /* ========== FIT DAO TESTING ========== */

            // TEST 1: Add a new fit
//            System.out.println("\n=== TEST 1: Adding a new fit ===");
//            Fit newFit = new Fit(2, "Casual Outfit test", topIndexID, bottomIndexID, shoeIndexID, Constants.NOT_FAVORITE);
//            fitDao.addFit(newFit);
//            System.out.println("Added fit with ID: " + newFit.getId());

            // TEST 2: Get all fits
            /*
            System.out.println("\n=== TEST 2: Getting all fits ===");
            List<Fit> allFits = fitDao.getAllFits();
            if (allFits.isEmpty()) {
                System.out.println("No fits found");
            } else {
                System.out.println("Found " + allFits.size() + " fits:");
                for (Fit fit : allFits) {
                    ClothingItem topItem = dao.getClothingItemById(fit.getTopId());
                    ClothingItem bottomItem = dao.getClothingItemById(fit.getBottomId());
                    ClothingItem shoesItem = dao.getClothingItemById(fit.getShoesId());

                    System.out.println(
                            "ID: " + fit.getId() + "\n" +
                                    "Name: " + fit.getName() + "\n" +
                                    "Top: " + (topItem != null ? topItem.getName() : "Unknown") + "\n" +
                                    "Bottom: " + (bottomItem != null ? bottomItem.getName() : "Unknown") + "\n" +
                                    "Shoes: " + (shoesItem != null ? shoesItem.getName() : "None") + "\n" +
                                    "Favorite: " + (fit.getIs_Favorite() == Constants.FAVORITE ? "Yes" : "No") + "\n"
                    );
                }
            }

             */

            // TEST 3: Add fit to favorites
/*
            int idToFavorite = 1; // Change this to any valid ID dynamically
            System.out.println("\n=== TEST: Adding Fit ID " + idToFavorite + " to Favorites ===");

            boolean fitExists = fitDao.getAllFits().stream()
                    .anyMatch(f -> f.getId() == idToFavorite);

            if (fitExists) {
                fitDao.addFitToFavorite(idToFavorite);

                // Verify update
                Fit updatedFit = fitDao.getAllFits().stream()
                        .filter(f -> f.getId() == idToFavorite)
                        .findFirst()
                        .orElse(null);

                if (updatedFit != null && updatedFit.getIs_Favorite() == Constants.FAVORITE) {
                    System.out.println("Successfully added to favorites.");
                } else {
                    System.out.println("Failed to update favorite status.");
                }
            } else {
                System.out.println("Fit with ID " + idToFavorite + " does not exist.");
            }

 */


            // TEST 4: Remove fit from favorites
/*

            int idToUnfavorite = 1; // Change this as needed
            System.out.println("\n=== TEST: Removing Fit ID " + idToUnfavorite + " from Favorites ===");

            boolean fitExists = fitDao.getAllFits().stream()
                    .anyMatch(f -> f.getId() == idToUnfavorite);

            if (fitExists) {
                fitDao.removeFitFromFavorite(idToUnfavorite);

                // Verify update
                Fit updatedFit = fitDao.getAllFits().stream()
                        .filter(f -> f.getId() == idToUnfavorite)
                        .findFirst()
                        .orElse(null);

                if (updatedFit != null && updatedFit.getIs_Favorite() == Constants.NOT_FAVORITE) {
                    System.out.println("Successfully removed from favorites.");
                } else {
                    System.out.println("Failed to update favorite status.");
                }
            } else {
                System.out.println("Fit with ID " + idToUnfavorite + " does not exist.");
            }

 */

            //TEST 5 : UPDATE FIT
            /*
            int idToUpdate = 1; // Change this as needed
            System.out.println("\n=== TEST: Updating Fit ID " + idToUpdate + " ===");

            // Check if fit exists
            Optional<Fit> optionalFit = fitDao.getAllFits().stream().filter(f -> f.getId() == idToUpdate).findFirst();

            if (optionalFit.isPresent()) {
                Fit fitToUpdate = optionalFit.get();

                // Modify fields as needed
                fitToUpdate.setName("Updated Fit Name");
                fitToUpdate.setTop(6);       // new topId
                fitToUpdate.setBottom(8);    // new bottomId
                fitToUpdate.setShoes(9);     // new shoesId
                // leave favorite status unchanged or update if needed

                fitDao.updateFit(fitToUpdate);

                // Verify update
                Fit updatedFit = fitDao.getAllFits().stream().filter(f -> f.getId() == idToUpdate).findFirst().orElse(null);

                if (updatedFit != null && updatedFit.getName().equals("Updated Fit Name") && updatedFit.getTopId() == 6 && updatedFit.getBottomId() == 8 && updatedFit.getShoesId() == 9) {
                    System.out.println("Fit update successful.");
                } else {
                    System.out.println("Fit update failed.");
                }
            } else {
                System.out.println("Fit with ID " + idToUpdate + " does not exist.");
            }

             */

            // TEST 6: Delete a fit
/*
            int idToDelete = 1;
            System.out.println("\n=== TEST: Deleting Fit with ID " + idToDelete + " ===");

            //List<Fit> allFits = fitDao.getAllFits();
            boolean fitExists = allFits.stream()
                    .anyMatch(f -> f.getId() == idToDelete);

            if (fitExists) {
                fitDao.deleteFit(idToDelete);

                // Verify deletion
                boolean stillExists = fitDao.getAllFits().stream()
                        .anyMatch(f -> f.getId() == idToDelete);

                System.out.println("Deletion of fit ID " + idToDelete + (stillExists ? " failed." : " successful."));
            } else {
                System.out.println("Fit with ID " + idToDelete + " does not exist.");
            }


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
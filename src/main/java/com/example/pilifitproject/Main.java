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

        //item testing CRUD
//        ClothingItem item = new ClothingItem(
//                7,
//                "Layered Sweater Polo",
//                "/images/tops/T1.png",
//                1,
//                1,
//                3,
//                "L",
//                0
//        );

       // ClothingItemDAO dao = new ClothingItemDAO();
        try {
//            dao.addClothingItem(item);
//            System.out.println("items added succesfully");
//            dao.deleteClothingItem(4);
//            dao.deleteClothingItem(5);
//            dao.updateClothingItem(item);
//            System.out.println("items updated successfully");


//            for(ClothingItem c: dao.getAllClothingItem()){
//                System.out.println( "ID          : " + c.getId() +
//                                    "\nName       : " + c.getName() +
//                                    "\nImagePath  : " + c.getImagePath() +
//                                    "\nCategoryID : " + c.getCategoryId() +
//                                    "\nColorID    : " + c.getColorId() +
//                                    "\nStyleID    : " + c.getStyleId() +
//                                    "\nSize       : " + c.getSize() +
//                                    "\nIsFavorite : " + c.getIsFavorite()
//                );
//            }
//            System.out.println();



            //getAllItemsByCategory test

//            int categoryIdTest = 1;
//
//            System.out.println("test categoryItems filter");
//
//            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByCategory(categoryIdTest);
//
//            if(items.isEmpty()){
//                System.out.println("no items found");
//            }else{
//                System.out.println("found: " + items.size() + " items");
//                for(ClothingItem item : items ){
//                    System.out.println("ID: " + item.getId());
//                    System.out.println("Name: " + item.getName());
//                    System.out.println("CategoryId: " + item.getCategoryId());
//                    System.out.println("ColorId: " + item.getColorId());
//                    System.out.println("StyleId: " + item.getStyleId());
//                    System.out.println("Size: " + item.getSize());
//                    System.out.println("Is favorite: " + item.getIsFavorite());
//
//                }
//            }


            //getAllItemsByColor test
//            int colorIdTest = 1;
//
//            System.out.println("test categoryItems filter");
//
//            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByColor(colorIdTest);
//
//            if(items.isEmpty()){
//                System.out.println("no items found");
//            }else{
//                System.out.println("found: " + items.size() + " items");
//                for(ClothingItem item : items ){
//                    System.out.println("ID: " + item.getId());
//                    System.out.println("Name: " + item.getName());
//                    System.out.println("CategoryId: " + item.getCategoryId());
//                    System.out.println("ColorId: " + item.getColorId());
//                    System.out.println("StyleId: " + item.getStyleId());
//                    System.out.println("Size: " + item.getSize());
//                    System.out.println("Is favorite: " + item.getIsFavorite());
//
//                }
//            }


            //getAllItemsByStyle test
//            int styleIDTest = 1;
//
//            System.out.println("test categoryItems filter");
//
//            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByStyleId(styleIDTest);
//
//            if(items.isEmpty()){
//                System.out.println("no items found");
//            }else{
//                System.out.println("found: " + items.size() + " items");
//                for(ClothingItem item : items ){
//                    System.out.println("ID: " + item.getId());
//                    System.out.println("Name: " + item.getName());
//                    System.out.println("CategoryId: " + item.getCategoryId());
//                    System.out.println("ColorId: " + item.getColorId());
//                    System.out.println("StyleId: " + item.getStyleId());
//                    System.out.println("Size: " + item.getSize());
//                    System.out.println("Is favorite: " + item.getIsFavorite());
//
//                }
//            }


            //getAllClothingItemsByIsFavorite
            // int is_favoriteTest = 1;

            System.out.println("test categoryItems filter");

            List<ClothingItem> items = ClothingItemDAO.getAllClothingItemsByIsFavorite(Constants.FAVORITE);

            if(items.isEmpty()){
                System.out.println("no items found");
            }else{
                System.out.println("found: " + items.size() + " items");
                for(ClothingItem item : items ){
                    System.out.println("ID: " + item.getId());
                    System.out.println("Name: " + item.getName());
                    System.out.println("CategoryId: " + item.getCategoryId());
                    System.out.println("ColorId: " + item.getColorId());
                    System.out.println("StyleId: " + item.getStyleId());
                    System.out.println("Size: " + item.getSize());
                    System.out.println("Is favorite: " + item.getIsFavorite());

                }
            }

        } catch (SQLiteException e) {
            System.out.println("failed");
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        //launch();


    }
}
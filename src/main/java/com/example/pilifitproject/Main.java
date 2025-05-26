package com.example.pilifitproject;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.DBConnection;
import com.example.pilifitproject.model.ClothingItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.sql.SQLException;

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

        ClothingItem item = new ClothingItem(
                5,
                "Black Leather Jacket",
                "/images/tops/T3.png",
                1,
                "Black",
                "Casual",
                "L",
                1
        );

        ClothingItemDAO dao = new ClothingItemDAO();
        try {
            dao.addClothingItem(item);
            System.out.println("items added succesfully");
            //dao.deleteClothingItem(2);
            //dao.deleteClothingItem(3);
            //dao.updateClothingItem(item);
            //System.out.println("items updated successfully");

            for(ClothingItem c: dao.getAllClothingItem()){
                System.out.println("ID : " + c.getId() +
                                    "\nName: " + c.getName() +
                                    "\nImagePath : " + c.getImagePath() +
                                    "\nCategoryID :" + c.getCategoryId() +
                                    "\nColor : " + c.getColor() +
                                    "\nStyle : " + c.getStyle() +
                                    "\nSize : " + c.getSize() +
                                    "\nIsFavorite : " + c.getIsFavorite()
                );
            }
        } catch (SQLiteException e) {
            System.out.println("item not added successfully");
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        launch();


    }
}
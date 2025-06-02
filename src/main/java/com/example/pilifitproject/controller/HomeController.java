package com.example.pilifitproject.controller;

import com.example.pilifitproject.model.ClothingItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView itemImageView;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public Image loadImageFromPath(String imagePath){
        try{
            InputStream is = getClass().getResourceAsStream(imagePath);
            if(is != null){
                return new Image(is);
            }else{
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }
        }catch (Exception e){
            System.out.println("Error loading image: " + imagePath);
            InputStream defaultIs = getClass().getResourceAsStream("/com/example/pilifitproject/images/top/T1.png");
            return new Image(defaultIs);
        }

    }


// images display test
    public void displayItem(ClothingItem item){
        try {
            Image image = loadImageFromPath(item.getImagePath());
            itemImageView.setImage(image);
            itemImageView.setFitWidth(200);
            itemImageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Failed to display item: " + e.getMessage());
        }

    }
}
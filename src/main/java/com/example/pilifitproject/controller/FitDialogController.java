package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.model.Fit;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class FitDialogController {
    @FXML
    public ImageView topImage;
    @FXML public ImageView bottomImage;
    @FXML public ImageView shoesImage;
    @FXML public TextField fitNameField;
    @FXML public Button deleteButton;

    public void setFit(Fit fit) throws SQLException {
        System.out.println("Setting fit: " + fit.getName()); // DEBUG
        ClothingItem top = new ClothingItemDAO().getClothingItemById(fit.getTopId());
        ClothingItem bottom = new ClothingItemDAO().getClothingItemById(fit.getBottomId());
        ClothingItem shoes = new ClothingItemDAO().getClothingItemById(fit.getShoesId());

        System.out.println("Top item: " + (top != null ? top.getName() : "null")); // DEBUG
        System.out.println("Image data sizes - " +
                "Top: " + (top != null ? top.getImageData().length : 0) + " bytes, " +
                "Bottom: " + (bottom != null ? bottom.getImageData().length : 0) + " bytes, " +
                "Shoes: " + (shoes != null ? shoes.getImageData().length : 0) + " bytes"); // DEBUG

        topImage.setImage(ImageUtil.bytesToImage(top.getImageData()));
        bottomImage.setImage(ImageUtil.bytesToImage(bottom.getImageData()));
        shoesImage.setImage(ImageUtil.bytesToImage(shoes.getImageData()));

        fitNameField.setText(fit.getName());
        fitNameField.setEditable(false); // Disable editing in grid view

        // Scale down delete button for grid view
        deleteButton.setPrefSize(30, 30);
        deleteButton.setLayoutX(220);
        deleteButton.setLayoutY(5);
    }
}

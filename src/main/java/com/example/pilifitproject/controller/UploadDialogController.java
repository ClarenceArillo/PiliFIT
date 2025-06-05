package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UploadDialogController {

    @FXML
    private Button UploadItem;

    @FXML
    public void initialize() {
        UploadItem.setOnAction(event -> openFileChooser());
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Clothing Item");

        // Optional: restrict to image files
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = (Stage) UploadItem.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            // Optional: Pass file back to HomeController or preview it
        } else {
            System.out.println("No file selected.");
        }







    }
    public void handleSave(){

        try {
            byte[] imageData = ImageUtil.fileToBytes(selectedFile);

            ClothingItem newItem = new ClothingItem(
                    0, // ID will be auto-generated
                    nameField.getText(),
                    imageData,
                    getSelectedCategoryId(),
                    getSelectedColorId(),
                    getSelectedStyleId(),
                    sizeField.getText(),
                    0 // Not favorite by default
            );

            new ClothingItemDAO().addClothingItem(newItem);

            imagePreview.getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private int getSelectedCategoryId() {
        // Implement based on your category selection logic
        return 1; // Example
    }

    private int getSelectedColorId() {
        // Implement based on your color selection logic
        return 1; // Example
    }

    private int getSelectedStyleId() {
        // Implement based on your style selection logic
        return 1; // Example
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

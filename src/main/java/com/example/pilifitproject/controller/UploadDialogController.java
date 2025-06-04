package com.example.pilifitproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
}

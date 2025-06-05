package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UploadDialogController {

    @FXML
    private Button UploadItem;
    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> colorComboBox;
    @FXML private ComboBox<String> styleComboBox;
    @FXML private TextField sizeField;

    private Stage dialogStage;
    private File selectedFile;

    @FXML
    public void initialize() {
        UploadItem.setOnAction(event -> openFileChooser());
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void closeDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Clothing Item");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        this.selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null) {
            openSaveConfirmation(selectedFile);
        }
    }

    private void openSaveConfirmation(File selectedFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/SaveConfirmation.fxml"));
            Parent root = loader.load();

            SaveConfirmationController controller = loader.getController();
            controller.setUploadDialogController(this, selectedFile);

            Stage confirmationStage = new Stage();
            confirmationStage.initModality(Modality.APPLICATION_MODAL);
            confirmationStage.setScene(new Scene(root));
            confirmationStage.setTitle("Confirm Save");
            confirmationStage.showAndWait();

        } catch (IOException e) {
            showAlert("Error", "Failed to open confirmation dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void handleSave(File selectedFile){

        try {
            // Validate inputs
            if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter a name for the clothing item");
                return;
            }

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

            ClothingItemDAO dao = new ClothingItemDAO();
            dao.addClothingItem(newItem);
            closeDialog();

        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to save item: " + e.getMessage());
            e.printStackTrace();
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

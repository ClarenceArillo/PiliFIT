package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.CategoryMapper;
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

    @FXML private Button UploadItem;
    @FXML private Button CloseButton;

    private static final String DEFAULT_NAME = "New Clothing Item";
    private static final String DEFAULT_CATEGORY = "Top"; // Default to Top
    private static final String DEFAULT_COLOR = "Black"; // Default to Black
    private static final String DEFAULT_STYLE = "Casual"; // Default to Casual
    private static final String DEFAULT_SIZE = "M";

    private Stage dialogStage;
    private File selectedFile;
    private HomeController homeController;

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }

    @FXML
    public void initialize() {
        if (UploadItem != null) {
            UploadItem.setOnAction(event -> openFileChooser());
        }
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
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

    public void handleSave(File selectedFile){

        try {
            byte[] imageData = ImageUtil.fileToBytes(selectedFile);
            System.out.println("Image converted to bytes, length: " + imageData.length); // Debug

            ClothingItem newItem = new ClothingItem(
                    0, // ID will be auto-generated
                    DEFAULT_NAME,
                    imageData,
                    CategoryMapper.getCategoryId(DEFAULT_CATEGORY),
                    CategoryMapper.getColorId(DEFAULT_COLOR),
                    CategoryMapper.getStyleId(DEFAULT_STYLE),
                    DEFAULT_SIZE,
                    0
            );

            System.out.println("Created new ClothingItem: " + newItem);
            new ClothingItemDAO().add(newItem);

            if (homeController != null) {
                homeController.refreshClothingItems();
            }

        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to save item: " + e.getMessage());
            e.printStackTrace();
        }
        forceCloseDialog();
    }


    public void forceCloseDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void openSaveConfirmation(File selectedFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/SaveConfirmation.fxml"));
            Parent root = loader.load();

            SaveConfirmationController controller = loader.getController();
            controller.setUploadDialogController(this, selectedFile);

            Stage confirmationStage = new Stage();
            confirmationStage.initOwner(dialogStage);
            controller.setDialogStage(confirmationStage);
            confirmationStage.setScene(new Scene(root));
            confirmationStage.setTitle("Confirm Save");
            confirmationStage.show();

        } catch (IOException e) {
            showAlert("Error", "Failed to open confirmation dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCancel() {
        closeDialog();
    }

    public void closeDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        } else {
            ((Stage) CloseButton.getScene().getWindow()).close();
        }
        System.out.println("Close Tab");
    }


}

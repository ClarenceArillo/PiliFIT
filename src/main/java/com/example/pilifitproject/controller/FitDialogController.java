package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.model.Fit;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FitDialogController {
    @FXML
    public ImageView topImage;
    @FXML public ImageView bottomImage;
    @FXML public ImageView shoesImage;
    @FXML public TextField fitNameField;
    @FXML public Button deleteButton;

    private Fit currentFit;
    private RefreshableController collectionController;

    @FXML
    public void initialize() {
        deleteButton.setOnAction(event -> handleDeleteFit());
    }

    public void setCollectionController(RefreshableController controller) {
        this.collectionController = controller;
    }

    public void setFit(Fit fit) throws SQLException {
        this.currentFit = fit;

        // Load items with null checks
        ClothingItemDAO dao = new ClothingItemDAO();
        try {
            ClothingItem top = dao.getClothingItemById(fit.getTopId());
            ClothingItem bottom = dao.getClothingItemById(fit.getBottomId());
            ClothingItem shoes = dao.getClothingItemById(fit.getShoesId());

            if (top != null && top.getImageData() != null) {
                topImage.setImage(ImageUtil.bytesToImage(top.getImageData()));
            }
            if (bottom != null && bottom.getImageData() != null) {
                bottomImage.setImage(ImageUtil.bytesToImage(bottom.getImageData()));
            }
            if (shoes != null && shoes.getImageData() != null) {
                shoesImage.setImage(ImageUtil.bytesToImage(shoes.getImageData()));
            }

            fitNameField.setText(fit.getName());

        } catch (Exception e) {
            System.err.println("Error loading fit images:");
            e.printStackTrace();
            showAlert("Error", "Could not load outfit images");
        }

        // Set text and UI properties
        fitNameField.setText(fit.getName());
        fitNameField.setEditable(false);

        // Position delete button
        deleteButton.setPrefSize(44, 44);
        deleteButton.setLayoutX(220);
        deleteButton.setLayoutY(5);
    }

    private void handleDeleteFit() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/pilifitproject/view/DeleteConfirmation.fxml"));
            Parent root = loader.load();

            DeleteConfirmationController controller = loader.getController();
            controller.setItemId(currentFit.getId());
            controller.setItemType(false); // This indicates it's a Fit deletion
            controller.setHomeController(collectionController);
            controller.setOnCancelCallback(this::closeDialog);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();


            if (controller.isDeleted()) {
                if (collectionController != null) {
                    collectionController.refreshClothingItems();
                }
                closeDialog();
            }

        } catch (IOException e) {
            showAlert("Error", "Failed to open delete confirmation");
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

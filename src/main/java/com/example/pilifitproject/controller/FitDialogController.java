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

            if (controller.isDeleted() && collectionController != null) {
                collectionController.refreshClothingItems();
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

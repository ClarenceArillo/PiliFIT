package com.example.pilifitproject.controller;

import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemDisplayController {
    @FXML private ImageView ImageViewContainer;
    @FXML private TextField ClothingItemNameDisplay;
    @FXML private Button ViewItemEdit;

    private ClothingItem clothingItem;
    private HomeController homeController;

    public void setClothingItem(ClothingItem item) {
        if (item == null) {
            System.err.println("Error: clothingItem is null!");
            return;
        }
        this.clothingItem = item;

        try {
            Image image = ImageUtil.bytesToImage(item.getImageData());
            ImageViewContainer.setImage(image);
            ClothingItemNameDisplay.setText(item.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void handleEditButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/ItemInformation.fxml"));
            Parent root = loader.load();

            ItemInformationController controller = loader.getController();
            controller.setClothingItem(clothingItem);
            controller.setHomeController(homeController);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the display after editing
            setClothingItem(clothingItem);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open edit dialog");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

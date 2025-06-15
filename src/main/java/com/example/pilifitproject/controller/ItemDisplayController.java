package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.Constants;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ItemDisplayController extends BaseController{

    @FXML private ImageView ImageViewContainer;
    @FXML private TextField ClothingItemNameDisplay;
    @FXML private Button favoriteBtn;

    private ClothingItem clothingItem;
    private RefreshableController homeController;

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
            updateFavoriteButton(); // Set favorite state when item is loaded
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHomeController(RefreshableController controller) {
        this.homeController = controller;
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

            refreshParentController();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open edit dialog");
        }
    }

    @FXML
    private void handleAddToFavorites() {
        if (clothingItem == null) return;

        try {
            // Toggle favorite status
            int newFavoriteStatus = clothingItem.getIsFavorite() == Constants.FAVORITE
                    ? Constants.NOT_FAVORITE
                    : Constants.FAVORITE;

            // Update in database
            new ClothingItemDAO().addClothingItemToFavorite(clothingItem.getId(), newFavoriteStatus);

            // Update local model
            clothingItem.setIsFavorite(newFavoriteStatus);

            // Update button appearance
            updateFavoriteButton();

            // Refresh parent view if needed
            if (homeController != null) {
                homeController.refreshClothingItems();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update favorite status");
        }
    }

    private void updateFavoriteButton() {
        if (clothingItem.getIsFavorite() == Constants.FAVORITE) {
            favoriteBtn.setText("❤️");
            favoriteBtn.setStyle("-fx-text-fill: red;");
        } else {
            favoriteBtn.setText("❤");
            favoriteBtn.setStyle("-fx-text-fill: gray;");
        }
    }

    private void refreshParentController() {
        if (homeController != null) {
            homeController.refreshClothingItems();
            homeController.refreshFavorites();
        }
    }

}

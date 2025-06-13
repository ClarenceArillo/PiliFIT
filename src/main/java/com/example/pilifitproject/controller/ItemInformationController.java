package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.CategoryMapper;
import com.example.pilifitproject.utils.Constants;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemInformationController {
        @FXML private ImageView ItemInformationImg;
        @FXML private TextField nameInput;
        @FXML private ComboBox<String> categoryDropdown;
        @FXML private ComboBox<String> styleDropdown;
        @FXML private ComboBox<String> colorDropdown;
        @FXML private TextField sizeInput;
        @FXML private Button SaveItemInfoBtn;
        @FXML private Button deleteBtn;
        @FXML private Button favoriteBtn;

        @FXML private Button closeButton;

        private ClothingItem clothingItem;
        private RefreshableController homeController;
        private Stage dialogStage;

        @FXML
        public void initialize() {
            categoryDropdown.getItems().addAll("Top", "Bottom", "Shoes");
            colorDropdown.getItems().addAll("Red", "Orange", "Yellow", "Green", "Blue",
                    "Violet", "White", "Black", "Others");
            styleDropdown.getItems().addAll("Formal", "Casual", "Semi-Formal", "Others");

            // Debug: Test CategoryMapper
            System.out.println("CategoryMapper Test:");
            System.out.println("Top → " + CategoryMapper.getCategoryId("Top")); // Should be 1
            System.out.println("1 → " + CategoryMapper.getCategoryName(1));     // Should be "Top"

            SaveItemInfoBtn.setOnAction(event -> handleSave());
            deleteBtn.setOnAction(event -> handleDeleteButton());
            favoriteBtn.setOnAction(event -> handleAddToFavorites());

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
            favoriteBtn.setText("❤️"); // Filled heart
            favoriteBtn.setStyle("-fx-text-fill: red;");
        } else {
            favoriteBtn.setText("❤"); // Changes -Empty heart to Filled heart
            favoriteBtn.setStyle("-fx-text-fill: gray;");
        }
    }


    public void setClothingItem(ClothingItem item) {

        if (item == null) {
            showAlert("Error", "No item data provided.");
            return;
        }

            this.clothingItem = item;
            loadItemData();
    }

    public void setHomeController(RefreshableController controller) {
        this.homeController = controller;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    private void loadItemData() {
        try {
            System.out.println("Loading item data for: " + clothingItem.getName()); // Debug

            if (clothingItem.getImageData() != null) {
                Image image = ImageUtil.bytesToImage(clothingItem.getImageData());
                ItemInformationImg.setImage(image);
            } else {
                System.err.println("Image data is null!");
            }

            // Set text fields
            nameInput.setText(clothingItem.getName());
            sizeInput.setText(clothingItem.getSize());
            updateFavoriteButton();

            // Set combo box selections
            categoryDropdown.setValue(CategoryMapper.getCategoryName(clothingItem.getCategoryId()));
            colorDropdown.setValue(CategoryMapper.getColorName(clothingItem.getColorId()));
            styleDropdown.setValue(CategoryMapper.getStyleName(clothingItem.getStyleId()));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load item data");
        }
    }

    @FXML
    private void handleSave() {
        if (nameInput.getText().isEmpty() || sizeInput.getText().isEmpty() ||
                categoryDropdown.getValue() == null ||
                colorDropdown.getValue() == null ||
                styleDropdown.getValue() == null) {
            showAlert("Validation Error", "Please fill all fields");
            return;
        }

        // Update the item with new values
        clothingItem.setName(nameInput.getText());
        clothingItem.setCategoryId(CategoryMapper.getCategoryId(categoryDropdown.getValue()));
        clothingItem.setColorId(CategoryMapper.getColorId(colorDropdown.getValue()));
        clothingItem.setStyleId(CategoryMapper.getStyleId(styleDropdown.getValue()));
        clothingItem.setSize(sizeInput.getText());

        if (homeController instanceof FavoritesController) {
            ((FavoritesController) homeController).refreshFavorites();
        } else if (homeController instanceof HomeController) {
            ((HomeController) homeController).refreshClothingItems();
        }

        try {
            new ClothingItemDAO().updateClothingItem(clothingItem);
            if (homeController != null) {
                homeController.refreshClothingItems();
                homeController.refreshFavorites();
            }

            // Close the dialog
            if (dialogStage != null) {
                dialogStage.close();
            } else {
                ((Stage) SaveItemInfoBtn.getScene().getWindow()).close();
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to update item: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    @FXML
    private void handleDeleteButton() {
        try {
            // Load the confirmation dialog
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/pilifitproject/view/DeleteConfirmation.fxml")
            );
            Parent root = loader.load();

            // Get the controller and pass the item ID
            DeleteConfirmationController controller = loader.getController();
            controller.setItemId(clothingItem.getId());
            controller.setHomeController(homeController);

            // Add this callback
            controller.setOnCancelCallback(() -> {
                closeDialog(); // This will close the ItemInformation dialog
            });

            // Show the dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            controller.setDialogStage(stage); // Pass the stage reference
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Close the edit dialog after deletion
            if (controller.isDeleted()) {
                closeDialog();
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open delete confirmation");
        }
    }


    private void closeDialog() {
//        if (dialogStage != null) {
//            dialogStage.close();
//        }
        Stage stage = (Stage) nameInput.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    private void handleExclusiveSelection(CheckMenuItem selectedItem) {
        for (CheckMenuItem item : allFilterItems) {
            if (item != selectedItem) {
                item.setSelected(false);
            }
        }

        // Optional: Print selected item for testing
        System.out.println("Selected: " + selectedItem.getText());
    }
    public Button getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(Button closeButton) {
        Button CloseButton = closeButton;
    }


    // In your ClosetController or similar

}

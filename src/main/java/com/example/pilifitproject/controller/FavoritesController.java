package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.Constants;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class FavoritesController extends BaseController implements RefreshableController {

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    @FXML private GridPane favItemsDisplay;


    // Add these constants
    private static final int COLUMNS = 6;
    private static final int ITEM_WIDTH = 120;
    private static final int ITEM_HEIGHT = 150;

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        switchScene(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        switchScene(event, "AboutUs.fxml");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        switchScene(event, "ContactUs.fxml");
    }

    @FXML
    private void goCollection(ActionEvent event) throws IOException {
        switchScene(event, "Collection.fxml");
    }

    @FXML
    private void goCloset(ActionEvent event) throws IOException {
        switchScene(event, "Home.fxml");
    }
    @FXML
    private void goFavorites(ActionEvent event) throws IOException {
        switchScene(event, "Favorites.fxml");
    }

    @FXML
    public void initialize() {
        loadFavoriteItems();
    }

    private void loadFavoriteItems() {
        try {
            // Clear existing items
            favItemsDisplay.getChildren().clear();

            // Get only favorite items (is_favorite = 1)
            List<ClothingItem> favorites = new ClothingItemDAO().getAllClothingItemsByIsFavorite(Constants.FAVORITE);

            // Add items to grid starting at (0,0)
            int row = 0;
            int col = 0;

            for (ClothingItem item : favorites) {
                addFavoriteItemToGrid(item, col, row);

                col++;
                if (col >= COLUMNS) {
                    col = 0;
                    row++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load favorites");
        }
    }

    private void addFavoriteItemToGrid(ClothingItem item, int col, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/pilifitproject/view/itemDisplay.fxml")
            );
            AnchorPane itemDisplay = loader.load();

            ItemDisplayController controller = loader.getController();
            controller.setClothingItem(item);
            controller.setHomeController(this); // For refresh capability

            GridPane.setConstraints(itemDisplay, col, row);
            favItemsDisplay.getChildren().add(itemDisplay);

        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to simple display
            VBox fallback = createSimpleItemBox(item);
            GridPane.setConstraints(fallback, col, row);
            favItemsDisplay.getChildren().add(fallback);
        }
    }

    private VBox createSimpleItemBox(ClothingItem item) {
        VBox box = new VBox(5);
        box.setPrefSize(ITEM_WIDTH, ITEM_HEIGHT);
        box.setStyle("-fx-background-color: #161718; -fx-padding: 5;");

        try {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(ITEM_WIDTH - 10);
            imageView.setFitHeight(ITEM_HEIGHT - 30);
            imageView.setPreserveRatio(true);

            Image image = ImageUtil.bytesToImage(item.getImageData());
            imageView.setImage(image);

            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");

            box.getChildren().addAll(imageView, nameLabel);
        } catch (Exception e) {
            box.getChildren().add(new Label("Error loading item"));
        }

        return box;
    }
    @Override
    public void refreshFavorites() {
    }

    @Override
    public void refreshClothingItems() {
        // Can be empty if not needed
    }

    private void handleExclusiveSelection(CheckMenuItem selectedItem) {
        for (CheckMenuItem item : allFilterItems) {
            if (item != selectedItem) {
                item.setSelected(false);
            }
        }

        System.out.println("Selected: " + selectedItem.getText());
    }

}

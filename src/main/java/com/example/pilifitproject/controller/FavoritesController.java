package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.SceneSwitcher;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.Constants;
import com.example.pilifitproject.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.naming.Referenceable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class FavoritesController implements RefreshableController {

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    @FXML
    private MenuButton categoryDropdown;     // CATEGORY
    @FXML
    private MenuButton styleDropdown;    // STYLE
    @FXML
    private MenuButton colorDropdown;   // COLOR

    @FXML private GridPane favItemsDisplay;


    private RefreshableController homeController;

    // Add these constants
    private static final int COLUMNS = 6;
    private static final int ITEM_WIDTH = 120;
    private static final int ITEM_HEIGHT = 150;

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "AboutUs2.fxml");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Contact.fxml");
    }

    @FXML
    private void goCollection(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Collection.fxml");
    }

    @FXML
    private void goCloset(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }
    @FXML
    private void goFavorites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Favorites.fxml");
    }

    @FXML
    public void initialize() {
        setupCategoryDropdown();
        setupStyleDropdown();
        setupColorDropdown();
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

    public void setHomeController(RefreshableController controller) {
        this.homeController = controller;
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
        loadFavoriteItems();
    }

    @Override
    public void refreshClothingItems() {
        // Can be empty if not needed
    }

//    private void refreshAfterEdit() {
//        if (homeController instanceof FavoritesController) {
//            ((FavoritesController) homeController).refreshFavorites();
//        } else if (homeController instanceof HomeController) {
//            ((HomeController) homeController).refreshClothingItems();
//        }
//    }

    private void refreshAfterEdit() {
        if (homeController != null) {
            homeController.refreshClothingItems();
            homeController.refreshFavorites();
        }
    }


    private void setupCategoryDropdown() {
        CheckMenuItem top = new CheckMenuItem("Top");
        CheckMenuItem bottom = new CheckMenuItem("Bottom");
        CheckMenuItem shoes = new CheckMenuItem("Shoes");

        categoryDropdown.getItems().addAll(top, bottom, shoes );
        allFilterItems.addAll(List.of(top, bottom, shoes));

        top.setOnAction(e -> handleExclusiveSelection(top));
        bottom.setOnAction(e -> handleExclusiveSelection(bottom));
        shoes.setOnAction(e -> handleExclusiveSelection(shoes));;

    }

    private void setupStyleDropdown() {
        CheckMenuItem formal = new CheckMenuItem("Formal");
        CheckMenuItem casual = new CheckMenuItem("Casual");
        CheckMenuItem semi = new CheckMenuItem("Semi-Formal");
        CheckMenuItem others = new CheckMenuItem("Others");


        styleDropdown.getItems().addAll(formal, casual, semi, others);
        allFilterItems.addAll(List.of(formal, casual, semi, others));

        formal.setOnAction(e -> handleExclusiveSelection(formal));
        casual.setOnAction(e -> handleExclusiveSelection(casual));
        semi.setOnAction(e -> handleExclusiveSelection(semi));
        others.setOnAction(e -> handleExclusiveSelection(others));
    }

    private void setupColorDropdown() {
        CheckMenuItem red = new CheckMenuItem("Red");
        CheckMenuItem orange = new CheckMenuItem("Orange");
        CheckMenuItem yellow = new CheckMenuItem("Yellow");
        CheckMenuItem green = new CheckMenuItem("Green");
        CheckMenuItem blue = new CheckMenuItem("Blue");
        CheckMenuItem violet = new CheckMenuItem("Violet");
        CheckMenuItem white = new CheckMenuItem("White");
        CheckMenuItem black = new CheckMenuItem("Black");
        CheckMenuItem others = new CheckMenuItem("Others");

        colorDropdown.getItems().addAll(red, orange, yellow, green, blue, violet, white, black, others);
        allFilterItems.addAll(List.of(red, orange, yellow, green, blue, violet, white, black, others));

        red.setOnAction(e -> handleExclusiveSelection(red));
        orange.setOnAction(e -> handleExclusiveSelection(orange));
        yellow.setOnAction(e -> handleExclusiveSelection(yellow));
        green.setOnAction(e -> handleExclusiveSelection(green));
        blue.setOnAction(e -> handleExclusiveSelection(blue));
        violet.setOnAction(e -> handleExclusiveSelection(violet));
        white.setOnAction(e -> handleExclusiveSelection(white));
        black.setOnAction(e -> handleExclusiveSelection(black));
        others.setOnAction(e -> handleExclusiveSelection(others));
    }

    private void handleCategoryFilter(CheckMenuItem item) {
        System.out.println("CATEGORY: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }

    private void handleStyleFilter(CheckMenuItem item) {
        System.out.println("STYLE: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }

    private void handleColorFilter(CheckMenuItem item) {
        System.out.println("COLOR: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }

    @FXML
    private void openUploadDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/UploadDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Piece");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleExclusiveSelection(CheckMenuItem selectedItem) {
        for (CheckMenuItem item : allFilterItems) {
            if (item != selectedItem) {
                item.setSelected(false);
            }
        }System.out.println("Selected: " + selectedItem.getText());
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

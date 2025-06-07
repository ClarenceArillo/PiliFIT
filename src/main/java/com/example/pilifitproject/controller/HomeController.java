package com.example.pilifitproject.controller;

import com.example.pilifitproject.SceneSwitcher;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.GeneratedFitPreview;
import com.example.pilifitproject.utils.ImageUtil;
import com.example.pilifitproject.utils.RandomFitGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    @FXML private GridPane itemsGridPane;
    @FXML private Button Addnew;

    private static final int COLUMNS = 6;
    private static final int ITEM_WIDTH = 120;
    private static final int ITEM_HEIGHT = 150;

    @FXML
    private MenuButton categoryDropdown;     // CATEGORY
    @FXML
    private MenuButton styleDropdown;    // STYLE
    @FXML
    private MenuButton colorDropdown;   // COLOR

    @FXML
    public void initialize() {
        setupCategoryDropdown();
        setupStyleDropdown();
        setupColorDropdown();

        refreshClothingItems();

        // Set up upload button
        Addnew.setOnAction(event -> openUploadDialog());
    }

    public HomeController() throws SQLException {
    }

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
        System.out.println("Fav Page");
    }

    private void addItemToGrid(ClothingItem item, int col, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/itemDisplay.fxml"));
            AnchorPane itemDisplay = loader.load();

            ItemDisplayController controller = loader.getController();
            controller.setClothingItem(item);

            controller.setHomeController(this);

            GridPane.setConstraints(itemDisplay, col, row);
            itemsGridPane.getChildren().add(itemDisplay);
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to simple VBox if FXML fails
            itemsGridPane.add(createItemBox(item), col, row);
        }
    }

    public void refreshClothingItems() {
        try {
            List<Node> toRemove = new ArrayList<>();
            for (Node node : itemsGridPane.getChildren()) {
                if (node != Addnew) {
                    toRemove.add(node);
                }
            }
            itemsGridPane.getChildren().removeAll(toRemove);

            // Get all clothing items from database
            List<ClothingItem> items = new ClothingItemDAO().getAllClothingItem();

            // Add items to grid
            int row = 0;
            int col = 1; // Start from column 1 to preserve the Add button

            for (ClothingItem item : items) {
                addItemToGrid(item, col, row);

                col++;
                if (col >= COLUMNS) {
                    col = 0;
                    row++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Show error alert
        }
        System.out.println("Button visible: " + Addnew.isVisible());
        System.out.println("Button managed: " + Addnew.isManaged());
    }


    private VBox createItemBox(ClothingItem item) {
        VBox box = new VBox(5);
        box.setPrefSize(ITEM_WIDTH, ITEM_HEIGHT);
        box.setStyle("-fx-background-color: #161718; -fx-padding: 5;");

        try {
            // Create image view
            ImageView imageView = new ImageView();
            imageView.setFitWidth(ITEM_WIDTH - 10);
            imageView.setFitHeight(ITEM_HEIGHT - 30);
            imageView.setPreserveRatio(true);

            // Convert BLOB to JavaFX Image
            Image image = ImageUtil.bytesToImage(item.getImageData());
            imageView.setImage(image);

            // Create label for item name
            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");
            nameLabel.setMaxWidth(ITEM_WIDTH - 10);
            nameLabel.setWrapText(true);

            box.getChildren().addAll(imageView, nameLabel);

        } catch (Exception e) {
            e.printStackTrace();
            box.getChildren().add(new Label("Error loading image"));
        }

        return box;
    }

    @FXML
    private void openUploadDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/UploadDialog.fxml"));
            Parent root = loader.load();

            UploadDialogController controller = loader.getController();
            controller.setHomeController(this); // Pass reference to refresh after upload

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //=====GENERATE Button Action=====

//    @FXML
//    private ImageView topImageView;
//    @FXML
//    private ImageView bottomImageView;
//    @FXML
//    private ImageView shoesImageView;
//    @FXML
//    private Button GenerateFitBtn;
//
//    private final RandomFitGenerator randomFitGenerator = new RandomFitGenerator();
//    private GeneratedFitPreview currentPreview;
//
//    @FXML
//    private void handleGenerateButtonAction() {
//        try {
//            // Generate the random fit
//            currentPreview = randomFitGenerator.generateRandomPreview();
//
//            // Load images into their views
//            loadImageIntoView(currentPreview.getTop().getImageData(), topImageView);
//            loadImageIntoView(currentPreview.getBottom().getImageData(), bottomImageView);
//            loadImageIntoView(currentPreview.getShoes().getImageData(), shoesImageView);
//
//        } catch (Exception event) {
//            //e.printStackTrace();
//            System.out.println("btn generate is not working properly");
//        }
//    }
//
//    GeneratedFitPreview fitPreview = randomFitGenerator.generateRandomPreview();
//    @FXML
//    private void loadImageIntoView(String imagePath, ImageView imageView) {
//        Image image = ImageUtil.loadImage(imagePath);  // This now resolves relative path correctly
//        imageView.setImage(image);
//    }


    //===GENERATE btn end

    //===Image Upload btn start


    //===Image Upload btn end

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

            UploadDialogController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Piece");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));

            controller.setDialogStage(dialogStage);
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
        }
    }
}
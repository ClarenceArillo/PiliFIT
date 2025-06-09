package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.SceneSwitcher;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.GeneratedFitPreview;
import com.example.pilifitproject.utils.ImageUtil;
import com.example.pilifitproject.utils.RandomFitGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController implements RefreshableController {

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    @FXML private GridPane itemsGridPane;
    @FXML private Button Addnew;

    private static final int COLUMNS = 6;
    private static final int ITEM_WIDTH = 120;
    private static final int ITEM_HEIGHT = 150;

    private Integer currentCategoryFilter = null;
    private Integer currentColorFilter = null;
    private Integer currentStyleFilter = null;

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

    @Override
    public void refreshClothingItems() {
        try {
            List<Node> toRemove = new ArrayList<>();
            for (Node node : itemsGridPane.getChildren()) {
                if (node != Addnew) {
                    toRemove.add(node);
                }
            }

            itemsGridPane.getChildren().removeIf(node -> !(node instanceof Button && ((Button) node).getId() != null && ((Button) node).getId().equals("Addnew")));

            // Get filtered items
            List<ClothingItem> items = new ClothingItemDAO().getFilteredClothingItems(
                    currentCategoryFilter,
                    currentColorFilter,
                    currentStyleFilter
            );

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

    @Override
    public void applyFilters(Integer categoryId, Integer colorId, Integer styleId) {
        this.currentCategoryFilter = categoryId;
        this.currentColorFilter = colorId;
        this.currentStyleFilter = styleId;
        refreshClothingItems();
    }

    @Override
    public void refreshFavorites() {
        // Can be empty if not needed
    }

    /*
    private void setupCategoryDropdown() {
        categoryDropdown.getItems().clear();
        ToggleGroup categoryGroup = new ToggleGroup();

        // Add "All Categories" option
        CheckMenuItem allCategories = new CheckMenuItem("All Categories");
        allCategories.setToggleGroup(categoryGroup);
        allCategories.setOnAction(e -> {
            currentCategoryFilter = null;
            refreshClothingItems();
        });

        // Add specific category options
        CheckMenuItem top = createFilterMenuItem("Top", 1, "category", categoryGroup);
        CheckMenuItem bottom = createFilterMenuItem("Bottom", 2, "category", categoryGroup);
        CheckMenuItem shoes = createFilterMenuItem("Shoes", 3, "category", categoryGroup);


        categoryDropdown.getItems().addAll(allCategories, top, bottom, shoes);
    }

    private void setupColorDropdown() {
        colorDropdown.getItems().clear();
        ToggleGroup colorGroup = new ToggleGroup();

        // Add "All Colors" option
        CheckMenuItem allColors = new CheckMenuItem("All Colors");
        allColors.setToggleGroup(colorGroup);
        allColors.setOnAction(e -> {
            currentColorFilter = null;
            refreshClothingItems();
        });

        // Add specific color options
        CheckMenuItem red = createFilterMenuItem("Red", 1, "color", colorGroup);
        CheckMenuItem orange = createFilterMenuItem("Orange", 2, "color", colorGroup);
        CheckMenuItem yellow = createFilterMenuItem("Yellow", 3, "color", colorGroup;
        CheckMenuItem green = createFilterMenuItem("Green", 4, "color", colorGroup);
        CheckMenuItem blue = createFilterMenuItem("Blue", 5, "color", colorGroup);
        CheckMenuItem violet = createFilterMenuItem("Violet", 6, "color", colorGroup);
        CheckMenuItem white = createFilterMenuItem("White", 7, "color", colorGroup);
        CheckMenuItem black = createFilterMenuItem("Black", 8, "color", colorGroup);
        CheckMenuItem othersColor = createFilterMenuItem("Others", 9, "color", colorGroup);

        colorDropdown.getItems().addAll(allColors, red, orange, yellow, green,
                blue, violet, white, black, othersColor);
    }

    private void setupStyleDropdown() {
        styleDropdown.getItems().clear();
        ToggleGroup styleGroup = new ToggleGroup();

        // Add "All Styles" option
        CheckMenuItem allStyles = new CheckMenuItem("All Styles");
        allStyles.setToggleGroup(styleGroup);
        allStyles.setOnAction(e -> {
            currentStyleFilter = null;
            refreshClothingItems();
        });

        // Add specific style options
        CheckMenuItem formal = createFilterMenuItem("Formal", 1, "style", styleGroup);
        CheckMenuItem casual = createFilterMenuItem("Casual", 2, "style", styleGroup);
        CheckMenuItem semiFormal = createFilterMenuItem("Semi-Formal", 3, "style", styleGroup);
        CheckMenuItem othersStyle = createFilterMenuItem("Others", 4, "style", styleGroup);

        styleDropdown.getItems().addAll(allStyles, formal, casual, semiFormal, othersStyle);
    }



    private CheckMenuItem createFilterMenuItem(String name, int id, String filterType, ToggleGroup toggleGroup) {
        CheckMenuItem menuItem = new CheckMenuItem(name);
        menuItem.setToggleGroup(toggleGroup);
        menuItem.setOnAction(e -> {
            switch (filterType) {
                case "category":
                    currentCategoryFilter = menuItem.isSelected() ? id : null;
                    break;
                case "color":
                    currentColorFilter = menuItem.isSelected() ? id : null;
                    break;
                case "style":
                    currentStyleFilter = menuItem.isSelected() ? id : null;
                    break;
            }
            refreshClothingItems();
        });
        return menuItem;
    }

*/

    private void setupCategoryDropdown() {
        categoryDropdown.getItems().clear();

        // Add "All Categories" option
        CheckMenuItem allCategories = new CheckMenuItem("All Categories");
        allCategories.setOnAction(e -> {
            clearCategorySelections();
            currentCategoryFilter = null;
            refreshClothingItems();
        });

        // Add specific category options
        CheckMenuItem top = createRadioLikeMenuItem("Top", 1, "category");
        CheckMenuItem bottom = createRadioLikeMenuItem("Bottom", 2, "category");
        CheckMenuItem shoes = createRadioLikeMenuItem("Shoes", 3, "category");

        categoryDropdown.getItems().addAll(allCategories, top, bottom, shoes);
    }

    private void setupColorDropdown() {
        colorDropdown.getItems().clear();

        // Add "All Colors" option
        CheckMenuItem allColors = new CheckMenuItem("All Colors");
        allColors.setOnAction(e -> {
            clearColorSelections();
            currentColorFilter = null;
            refreshClothingItems();
        });

        // Add specific color options
        CheckMenuItem red = createRadioLikeMenuItem("Red", 1, "color");
        CheckMenuItem orange = createRadioLikeMenuItem("Orange", 2, "color");
        CheckMenuItem yellow = createRadioLikeMenuItem("Yellow", 3, "color");
        CheckMenuItem green = createRadioLikeMenuItem("Green", 4, "color");
        CheckMenuItem blue = createRadioLikeMenuItem("Blue", 5, "color");
        CheckMenuItem violet = createRadioLikeMenuItem("Violet", 6, "color");
        CheckMenuItem white = createRadioLikeMenuItem("White", 7, "color");
        CheckMenuItem black = createRadioLikeMenuItem("Black", 8, "color");
        CheckMenuItem othersColor = createRadioLikeMenuItem("Others", 9, "color");


        colorDropdown.getItems().addAll(allColors, red, orange, yellow, green,
                blue, violet, white, black, othersColor);
    }

    private void setupStyleDropdown() {
        styleDropdown.getItems().clear();

        // Add "All Styles" option
        CheckMenuItem allStyles = new CheckMenuItem("All Styles");
        allStyles.setOnAction(e -> {
            clearStyleSelections();
            currentStyleFilter = null;
            refreshClothingItems();
        });

        // Add specific style options
        CheckMenuItem formal = createRadioLikeMenuItem("Formal", 1, "style");
        CheckMenuItem casual = createRadioLikeMenuItem("Casual", 2, "style");
        CheckMenuItem semiFormal = createRadioLikeMenuItem("Semi-Formal", 3, "style");
        CheckMenuItem othersStyle = createRadioLikeMenuItem("Others", 4, "style");

        styleDropdown.getItems().addAll(allStyles, formal, casual, semiFormal, othersStyle);
    }

    private CheckMenuItem createRadioLikeMenuItem(String name, int id, String filterType) {
        CheckMenuItem menuItem = new CheckMenuItem(name);
        menuItem.setOnAction(e -> {
            // Clear other selections in this group
            clearSelections(filterType);

            // Set this item as selected
            menuItem.setSelected(true);

            // Update the filter
            switch (filterType) {
                case "category":
                    currentCategoryFilter = id;
                    break;
                case "color":
                    currentColorFilter = id;
                    break;
                case "style":
                    currentStyleFilter = id;
                    break;
            }
            refreshClothingItems();
        });
        return menuItem;
    }

    private void clearSelections(String filterType) {
        switch (filterType) {
            case "category":
                clearCategorySelections();
                break;
            case "color":
                clearColorSelections();
                break;
            case "style":
                clearStyleSelections();
                break;
        }
    }

    private void clearCategorySelections() {
        for (MenuItem item : categoryDropdown.getItems()) {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).setSelected(false);
            }
        }
    }

    private void clearColorSelections() {
        for (MenuItem item : colorDropdown.getItems()) {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).setSelected(false);
            }
        }
    }

    private void clearStyleSelections() {
        for (MenuItem item : styleDropdown.getItems()) {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).setSelected(false);
            }
        }
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
/*
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

 */

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
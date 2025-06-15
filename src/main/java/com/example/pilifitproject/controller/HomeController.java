package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.FitService;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController extends BaseController implements RefreshableController {

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

    @FXML private ImageView topImageContainer;
    @FXML private ImageView bottomImageContainer;
    @FXML private ImageView shoesImageContainer;
    @FXML private Button generateRandomFitBtn;
    @FXML private Button saveFitBtn;
    @FXML private Button leftTop, rightTop;
    @FXML private Button leftBottom, rightBottom;
    @FXML private Button leftShoes, rightShoes;

    private GeneratedFitPreview currentPreview;
    private final RandomFitGenerator randomFitGenerator = new RandomFitGenerator();
    private final FitService fitService = new FitService();

    @FXML
    public void initialize() {
        setupCategoryDropdown();
        setupStyleDropdown();
        setupColorDropdown();
        refreshClothingItems();
        // Set up upload button
        Addnew.setOnAction(event -> openUploadDialog());

        setupFitGeneration();
        generateInitialFit();
    }

    public HomeController() throws SQLException {
    }

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
    public void refreshAll() {
        // Custom implementation if needed, or omit to use default
        refreshClothingItems();
        refreshFavorites();
        // Any additional Home-specific refreshes
    }

    public void applyFilters(Integer categoryId, Integer colorId, Integer styleId) {
        this.currentCategoryFilter = categoryId;
        this.currentColorFilter = colorId;
        this.currentStyleFilter = styleId;
        refreshAll();
    }

    @Override
    public void refreshFavorites() {
        refreshFavorites();
        // Can be empty if not needed
    }

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

    //=====GENERATE Button Action=====

    private void setupFitGeneration() {
        // Set up button actions
        generateRandomFitBtn.setOnAction(e -> generateNewFit());
        saveFitBtn.setOnAction(e -> saveCurrentFit());

        // Set up navigation buttons
        leftTop.setOnAction(e -> navigateItem("top", -1));
        rightTop.setOnAction(e -> navigateItem("top", 1));
        leftBottom.setOnAction(e -> navigateItem("bottom", -1));
        rightBottom.setOnAction(e -> navigateItem("bottom", 1));
        leftShoes.setOnAction(e -> navigateItem("shoes", -1));
        rightShoes.setOnAction(e -> navigateItem("shoes", 1));
    }

    private void generateInitialFit() {
        generateNewFit();
    }

    private void generateNewFit() {
        try {
            currentPreview = randomFitGenerator.generateRandomPreview();
            updatePreviewImages();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to generate outfit");
        }
    }

    private void updatePreviewImages() {
        try {
            Image topImage = ImageUtil.bytesToImage(currentPreview.getTop().getImageData());
            Image bottomImage = ImageUtil.bytesToImage(currentPreview.getBottom().getImageData());
            Image shoesImage = ImageUtil.bytesToImage(currentPreview.getShoes().getImageData());

            topImageContainer.setImage(topImage);
            bottomImageContainer.setImage(bottomImage);
            shoesImageContainer.setImage(shoesImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateItem(String category, int direction) {
        try {

            System.out.println("Navigating " + category + " direction: " + direction); //debug
            ClothingItem currentItem = getCurrentItemByCategory(category);
            List<ClothingItem> categoryItems = getItemsByCategory(category);

            if (categoryItems != null && !categoryItems.isEmpty()) {
                System.out.println("Found " + categoryItems.size() + " " + category + " items"); //debug
                // Find current position or start at 0 if not found
                int currentIndex = categoryItems.indexOf(currentItem);
                for (int i = 0; i < categoryItems.size(); i++) {
                    if (categoryItems.get(i).getId() == currentItem.getId()) {
                        currentIndex = i;
                        break;
                    }
                }
                if (currentIndex == -1) {
                    System.out.println("Current item not found in list, using first item");
                    currentIndex = 0;
                }

                // Calculate new index with wrap-around

                int newIndex = (currentIndex + direction + categoryItems.size()) % categoryItems.size();
                ClothingItem newItem = categoryItems.get(newIndex);

                // Update the preview with new item
                updatePreviewWithNewItem(category, newItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate items");
        }
    }

    private ClothingItem getCurrentItemByCategory(String category) {
        switch (category) {
            case "top": return currentPreview.getTop();
            case "bottom": return currentPreview.getBottom();
            case "shoes": return currentPreview.getShoes();
            default: return null;
        }
    }

    private List<ClothingItem> getItemsByCategory(String category) throws SQLException {
        switch (category) {
            case "top": return new ClothingItemDAO().getAllClothingItemsByCategory(1);
            case "bottom": return new ClothingItemDAO().getAllClothingItemsByCategory(2);
            case "shoes": return new ClothingItemDAO().getAllClothingItemsByCategory(3);
            default: return null;
        }
    }

    private void updatePreviewWithNewItem(String category, ClothingItem newItem) {
        System.out.println("Updating " + category + " with item ID: " + newItem.getId()); // Debug
        switch (category) {
            case "top":
                currentPreview = new GeneratedFitPreview(newItem, currentPreview.getBottom(), currentPreview.getShoes());
                topImageContainer.setImage(ImageUtil.bytesToImage(newItem.getImageData()));
                break;
            case "bottom":
                currentPreview = new GeneratedFitPreview(currentPreview.getTop(), newItem, currentPreview.getShoes());
                bottomImageContainer.setImage(ImageUtil.bytesToImage(newItem.getImageData()));
                break;
            case "shoes":
                currentPreview = new GeneratedFitPreview(currentPreview.getTop(), currentPreview.getBottom(), newItem);
                shoesImageContainer.setImage(ImageUtil.bytesToImage(newItem.getImageData()));
                break;
        }
    }

    private void saveCurrentFit() {
        if (currentPreview != null) {
            try {
                fitService.saveGeneratedFit("Generated Fit", currentPreview);
                showAlert("Success", "Fit saved successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save fit");
            }
        }
    }

    //===GENERATE btn end

}
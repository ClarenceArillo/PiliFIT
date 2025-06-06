package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.CategoryMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemInformationController {
        @FXML private TextField nameInput;
        @FXML private ComboBox<String> categoryDropdown;
        @FXML private ComboBox<String> styleDropdown;
        @FXML private ComboBox<String> colorDropdown;
        @FXML private TextField sizeInput;

        private ClothingItem item;
        private Stage dialogStage;

        @FXML
        public void initialize() {
            categoryDropdown.getItems().addAll("Top", "Bottom", "Shoes");
            colorDropdown.getItems().addAll("Red", "Orange", "Yellow", "Green", "Blue",
                    "Violet", "White", "Black", "Others");
            styleDropdown.getItems().addAll("Formal", "Casual", "Semi-Formal", "Others");
        }

    private void setCurrentSelections() {
        // Set category selection
        for (String category : categoryDropdown.getItems()) {
            if (CategoryMapper.getCategoryId(category) == item.getCategoryId()) {
                categoryDropdown.setValue(category);
                break;
            }
        }

        // Set color selection
        for (String color : colorDropdown.getItems()) {
            if (CategoryMapper.getColorId(color) == item.getColorId()) {
                colorDropdown.setValue(color);
                break;
            }
        }

        // Set style selection
        for (String style : styleDropdown.getItems()) {
            if (CategoryMapper.getStyleId(style) == item.getStyleId()) {
                styleDropdown.setValue(style);
                break;
            }
        }
    }


    @FXML
    private void handleSave() {
        // Update the item with new values
        item.setName(nameInput.getText());
        item.setCategoryId(CategoryMapper.getCategoryId(categoryDropdown.getValue()));
        item.setColorId(CategoryMapper.getColorId(colorDropdown.getValue()));
        item.setStyleId(CategoryMapper.getStyleId(styleDropdown.getValue()));
        item.setSize(sizeInput.getText());

        try {
            new ClothingItemDAO().updateClothingItem(item);
            closeDialog();
        } catch (SQLException e) {
            showAlert("Error", "Failed to update item: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
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

    // In your ClosetController or similar

}

package com.example.pilifitproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;

import java.util.ArrayList;
import java.util.List;

public class ClosetController {

    @FXML
    private MenuButton categoryDropdown;

    @FXML
    private MenuButton styleDropdown;

    private final List<CheckMenuItem> allFilterItems = new ArrayList<>();

    @FXML
    public void initialize() {
        setupCategoryDropdown();
        setupStyleDropdown();
    }

    private void setupCategoryDropdown() {
        CheckMenuItem top = new CheckMenuItem("Top");
        CheckMenuItem bottom = new CheckMenuItem("Bottom");
        CheckMenuItem shoes = new CheckMenuItem("Shoes");

        categoryDropdown.getItems().addAll(top, bottom, shoes);
        allFilterItems.addAll(List.of(top, bottom, shoes));

        top.setOnAction(e -> handleExclusiveSelection(top));
        bottom.setOnAction(e -> handleExclusiveSelection(bottom));
        shoes.setOnAction(e -> handleExclusiveSelection(shoes));
    }

    private void setupStyleDropdown() {
        CheckMenuItem formal = new CheckMenuItem("Formal");
        CheckMenuItem casual = new CheckMenuItem("Casual");
        CheckMenuItem semiFormal = new CheckMenuItem("Semi-Formal");
        CheckMenuItem others = new CheckMenuItem("Others");

        styleDropdown.getItems().addAll(formal, casual, semiFormal, others);
        allFilterItems.addAll(List.of(formal, casual, semiFormal, others));

        formal.setOnAction(e -> handleExclusiveSelection(formal));
        casual.setOnAction(e -> handleExclusiveSelection(casual));
        semiFormal.setOnAction(e -> handleExclusiveSelection(semiFormal));
        others.setOnAction(e -> handleExclusiveSelection(others));
    }

    private void handleExclusiveSelection(CheckMenuItem selectedItem) {
        for (CheckMenuItem item : allFilterItems) {
            if (item != selectedItem) {
                item.setSelected(false);
            }
        }

        // Optional: Print selected item for testing
        System.out.println("Selected: " + selectedItem.getText());
    }
}

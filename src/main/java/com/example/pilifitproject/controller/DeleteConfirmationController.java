package com.example.pilifitproject.controller;

import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.controller.HomeController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DeleteConfirmationController {
    @FXML private Button DeleteItemConfirmation;
    @FXML private Button CancelDeleteItemConfirmation;

    private int itemId;
    private HomeController homeController;
    private boolean isDeleted = false;
    private Stage dialogStage;
    private Runnable onCancelCallback;  // New callback function

    public void setItemId(int id) {
        this.itemId = id;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setOnCancelCallback(Runnable callback) {
        this.onCancelCallback = callback;
    }

    @FXML
    public void initialize() {
        DeleteItemConfirmation.setOnAction(event -> confirmDelete());
        CancelDeleteItemConfirmation.setOnAction(event -> cancelDelete());
    }

    private void confirmDelete() {
        try {
            new ClothingItemDAO().deleteClothingItem(itemId);
            isDeleted = true;


            if (homeController != null || dialogStage != null) {
                homeController.refreshClothingItems();
                dialogStage.close();
            }else {
                ((Stage) CancelDeleteItemConfirmation.getScene().getWindow()).close();
            }


            // Execute the callback to close ItemInformation
            if (onCancelCallback != null) {
                onCancelCallback.run();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cancelDelete() {
        // Just close the dialog without deleting
        ((Stage) CancelDeleteItemConfirmation.getScene().getWindow()).close();
    }
}
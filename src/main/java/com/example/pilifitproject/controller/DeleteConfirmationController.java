package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.FitDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DeleteConfirmationController extends BaseController {
    @FXML private Button DeleteItemConfirmation;
    @FXML private Button CancelDeleteItemConfirmation;

    private int itemId;
    private RefreshableController homeController;
    private boolean isDeleted = false;
    private boolean isClothingItem = true;
    private Runnable onCancelCallback;  // New callback function

    public void setItemId(int id) {
        this.itemId = id;
    }

    public void setHomeController(RefreshableController controller) {
        this.homeController = controller;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setOnCancelCallback(Runnable callback) {
        this.onCancelCallback = callback;
    }

    public void setItemType(boolean isClothingItem) {
        this.isClothingItem = isClothingItem;
    }

    @FXML
    public void initialize() {
        DeleteItemConfirmation.setOnAction(event -> confirmDelete());
        CancelDeleteItemConfirmation.setOnAction(event -> cancelDelete());
    }

    private void confirmDelete() {
        try {
            if (isClothingItem) {
                // Existing clothing item deletion
                new ClothingItemDAO().deleteClothingItem(itemId);
            } else {
                // New fit deletion
                new FitDAO().deleteFit(itemId);
            }
            isDeleted = true;

            //method for calling closing dialog
            closeDialog();

            // Then trigger refresh
            if (homeController != null) {
                homeController.refreshClothingItems();
                homeController.refreshFavorites();
            }


            // Execute the callback to close ItemInformation
            if (onCancelCallback != null) {
                onCancelCallback.run();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeDialog() {
        // First close the confirmation dialog
        if (dialogStage != null) {
            dialogStage.close();
        } else {
            ((Stage) DeleteItemConfirmation.getScene().getWindow()).close();
        }
        System.out.println("Close Tab");
    }

    private void cancelDelete() {
        // Just close the dialog without deleting
        ((Stage) CancelDeleteItemConfirmation.getScene().getWindow()).close();

    }
}
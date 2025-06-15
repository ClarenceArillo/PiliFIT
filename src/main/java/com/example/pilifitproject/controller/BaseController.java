package com.example.pilifitproject.controller;

import com.example.pilifitproject.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    protected Stage dialogStage;

    // Common dialog handling
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    protected void closeDialog() {
        if (dialogStage != null) dialogStage.close();
    }

    // Reusable scene switching (optional)
    protected void switchScene(ActionEvent event, String fxml) throws IOException {
        SceneSwitcher.switchTo(event, fxml);
    }

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

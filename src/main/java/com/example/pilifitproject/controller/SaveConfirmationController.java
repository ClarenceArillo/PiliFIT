package com.example.pilifitproject.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;

public class SaveConfirmationController {
    private UploadDialogController uploadDialogController;
    private File selectedFile;

    @FXML
    private Button DiscardChangesBtn;

    @FXML
    private Button SaveEditsBtn;

    public void setUploadDialogController(UploadDialogController controller, File file) {
        this.uploadDialogController = controller;
        this.selectedFile = file;
    }

    @FXML
    private void handleDiscardChanges() {
        // Close both dialogs
        Stage stage = (Stage) DiscardChangesBtn.getScene().getWindow();
        stage.close();

        // Also close the upload dialog if needed
        if (uploadDialogController != null) {
            uploadDialogController.closeDialog();
        }
    }

    @FXML
    private void handleSaveEdits() {
        if (uploadDialogController != null && selectedFile != null) {
            uploadDialogController.handleSave(selectedFile);
        }
        // Close confirmation dialog
        Stage stage = (Stage) SaveEditsBtn.getScene().getWindow();
        stage.close();
    }
}
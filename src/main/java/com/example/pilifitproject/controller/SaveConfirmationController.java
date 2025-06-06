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
        System.out.println("SaveEdits clicked"); // Debug 1
        System.out.println("UploadDialogController is null? " + (uploadDialogController == null)); // Debug 2
        System.out.println("Selected file is null? " + (selectedFile == null)); // Debug 3

        if (uploadDialogController != null && selectedFile != null) {
            System.out.println("Calling handleSave on uploadDialogController"); // Debug 4
            uploadDialogController.handleSave(selectedFile);
        }else {
            System.out.println("Skipping handleSave - controller or file is null"); // Debug 5
        }
        // Close confirmation dialog
        Stage stage = (Stage) SaveEditsBtn.getScene().getWindow();
        stage.close();
    }
}
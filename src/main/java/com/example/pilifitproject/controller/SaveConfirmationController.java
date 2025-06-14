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
    private Stage dialogStage;

    @FXML
    private Button DiscardChangesBtn;
    @FXML private Button CloseButton;

    @FXML
    private Button SaveEditsBtn;

    private Runnable onSaveCallback;

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setUploadDialogController(UploadDialogController controller, File file) {
        this.uploadDialogController = controller;
        this.selectedFile = file;
    }

    @FXML
    private void handleDiscardChanges() {
//        Close both dialogs
        Stage stage = (Stage) DiscardChangesBtn.getScene().getWindow();
        stage.close();

//        // Also close the upload dialog if needed
        uploadDialogController.closeDialog();

        // Close the upload dialog through its controller
        if (uploadDialogController != null) {
            uploadDialogController.forceCloseDialog();
        }
    }

    @FXML
    private void handleSaveEdits() {
        System.out.println("SaveEdits clicked"); // Debug 1
        System.out.println("UploadDialogController is null? " + (uploadDialogController == null)); // Debug 2
        System.out.println("Selected file is null? " + (selectedFile == null)); // Debug 3

        if (onSaveCallback != null) {
            onSaveCallback.run();
        }

        if (uploadDialogController != null && selectedFile != null) {
            System.out.println("Calling handleSave on uploadDialogController"); // Debug 4
            uploadDialogController.handleSave(selectedFile);
        }


        // Close confirmation dialog
        Stage stage = (Stage) SaveEditsBtn.getScene().getWindow();
        stage.close();
        uploadDialogController.closeDialog();
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        } else {
            ((Stage) CloseButton.getScene().getWindow()).close();
        }
        System.out.println("Close Tab");
    }

}
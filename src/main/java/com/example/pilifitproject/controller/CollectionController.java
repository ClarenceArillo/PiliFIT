package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.FitDAO;
import com.example.pilifitproject.model.Fit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.io.IOException;
import java.util.List;

public class CollectionController extends BaseController implements RefreshableController {
    @FXML private GridPane fitItemGrid;
    private FitDAO fitDAO = new FitDAO();
    private ClothingItemDAO clothingItemDAO = new ClothingItemDAO();

    @FXML
    public void initialize() {
        refreshClothingItems();
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
    private void goFavorites(ActionEvent event) throws IOException {
        switchScene(event, "Favorites.fxml");
    }

    @FXML
    private void goCollection(ActionEvent event) throws IOException {
        switchScene(event, "Collection.fxml");
    }
    @FXML
    private void goCloset(ActionEvent event) throws IOException {
        switchScene(event, "Home.fxml");
    }

    @Override
    public void refreshClothingItems() {
                try {
                    fitItemGrid.getChildren().clear();
                    List<Fit> fits = fitDAO.getAllFits();

                    // Clear existing constraints
                    fitItemGrid.getColumnConstraints().clear();
                    fitItemGrid.getRowConstraints().clear();

                    // Add 4 equal columns
                    for (int i = 0; i < 5; i++) {
                        fitItemGrid.getColumnConstraints().add(new ColumnConstraints(205));
                    }

                    int col = 0;
                    int row = 0;

                    for (Fit fit : fits) {
                        addFitToGrid(fit, col, row);
                        col++;
                        if (col >= 5) {
                            col = 0;
                            row++;
                            fitItemGrid.getRowConstraints().add(new RowConstraints(255));
                        }
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error loading fits: " + e.getMessage()).show();
                }
        }

    @Override
    public void refreshFavorites() {}

    private void addFitToGrid(Fit fit, int col, int row) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/FitDialog.fxml"));
        AnchorPane fitDialog = loader.load();

        FitDialogController controller = loader.getController();
        controller.setFit(fit);
        controller.setCollectionController(this);

        fitDialog.setPrefSize(205, 255);
        fitDialog.setMaxSize(205, 255);

        // Explicitly add column/row constraints
        if (fitItemGrid.getColumnConstraints().size() <= col) {
            fitItemGrid.getColumnConstraints().add(new ColumnConstraints(205));
        }
        if (fitItemGrid.getRowConstraints().size() <= row) {
            fitItemGrid.getRowConstraints().add(new RowConstraints(255));
        }

        GridPane.setConstraints(fitDialog, col, row);
        fitItemGrid.getChildren().add(fitDialog);
    }

}
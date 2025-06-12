package com.example.pilifitproject.controller;

import com.example.pilifitproject.RefreshableController;
import com.example.pilifitproject.SceneSwitcher;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.dao.FitDAO;
import com.example.pilifitproject.model.Fit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.List;

public class CollectionController implements RefreshableController {
    @FXML private GridPane fitItemGrid;
    private FitDAO fitDAO = new FitDAO();
    private ClothingItemDAO clothingItemDAO = new ClothingItemDAO();

    @FXML
    public void initialize() {
        refreshClothingItems();
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "AboutUs.fxml");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "ContactUs.fxml");
    }

    @FXML
    private void goFavorites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Favorites.fxml");
    }

    @FXML
    private void goCollection(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Collection.fxml");
    }
    @FXML
    private void goCloset(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }

    @Override
    public void refreshClothingItems() {
        try {
            System.out.println("Refreshing clothing items..."); // DEBUG
            fitItemGrid.getChildren().clear();

            List<Fit> fits = fitDAO.getAllFits();
            int col = 0;
            int row = 0;

            for (Fit fit : fits) {
                addFitToGrid(fit, col, row);

                col++;
                if (col >= 4) { // 4 columns based on your GridPane
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error refreshing items:");
            e.printStackTrace();
        }
    }

    @Override
    public void refreshFavorites() {

    }

    @Override
    public void applyFilters(Integer categoryId, Integer colorId, Integer styleId) {

    }

    private void addFitToGrid(Fit fit, int col, int row) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/FitDialog.fxml"));
        AnchorPane fitDialog = loader.load();

        // Set fixed size to match grid cells
        //fitDialog.setPrefSize(261, 305); // Matches your column constraints

        fitDialog.setMaxSize(261, 305);
        fitDialog.setMinSize(261, 305);

//        FitDialogController controller = loader.getController();
//        controller.setFit(fit);

        // Explicitly add column/row constraints
        if (fitItemGrid.getColumnConstraints().size() <= col) {
            fitItemGrid.getColumnConstraints().add(new ColumnConstraints(261));
        }
        if (fitItemGrid.getRowConstraints().size() <= row) {
            fitItemGrid.getRowConstraints().add(new RowConstraints(305));
        }

        GridPane.setConstraints(fitDialog, col, row);
        fitItemGrid.getChildren().add(fitDialog);
    }


}
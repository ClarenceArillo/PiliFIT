package com.example.pilifitproject.controller;

import com.example.pilifitproject.SceneSwitcher;
import com.example.pilifitproject.dao.ClothingItemDAO;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.GeneratedFitPreview;
import com.example.pilifitproject.utils.ImageUtil;
import com.example.pilifitproject.utils.RandomFitGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private MenuButton categoryDropdown;     // CATEGORY
    @FXML
    private MenuButton styleDropdown;    // STYLE
    @FXML
    private MenuButton colorDropdown;   // COLOR

    public HomeController() throws SQLException {
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "AboutUs2.fxml");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Contact.fxml");
    }

    @FXML
    public void initialize() {
        setupCategoryDropdown();
        setupStyleDropdown();
        setupColorDropdown();
        //image test

    }

    //=====GENERATE Button Action=====

//    @FXML
//    private ImageView topImageView;
//    @FXML
//    private ImageView bottomImageView;
//    @FXML
//    private ImageView shoesImageView;
//    @FXML
//    private Button GenerateFitBtn;
//
//    private final RandomFitGenerator randomFitGenerator = new RandomFitGenerator();
//    private GeneratedFitPreview currentPreview;
//
//    @FXML
//    private void handleGenerateButtonAction() {
//        try {
//            // Generate the random fit
//            currentPreview = randomFitGenerator.generateRandomPreview();
//
//            // Load images into their views
//            loadImageIntoView(currentPreview.getTop().getImagePath(), topImageView);
//            loadImageIntoView(currentPreview.getBottom().getImagePath(), bottomImageView);
//            loadImageIntoView(currentPreview.getShoes().getImagePath(), shoesImageView);
//
//        } catch (Exception event) {
//            //e.printStackTrace();
//            System.out.println("btn generate is not working properly");
//        }
//    }
//
//    GeneratedFitPreview fitPreview = randomFitGenerator.generateRandomPreview();
//    @FXML
//    private void loadImageIntoView(String imagePath, ImageView imageView) {
//        Image image = ImageUtil.loadImage(imagePath);  // This now resolves relative path correctly
//        imageView.setImage(image);
//    }


    //===GENERATE btn end

    //===Image Upload btn start


    //===Image Upload btn end

    private void setupCategoryDropdown() {
        CheckMenuItem top = new CheckMenuItem("Top");
        CheckMenuItem bottom = new CheckMenuItem("Bottom");
        CheckMenuItem shoes = new CheckMenuItem("Shoes");


        categoryDropdown.getItems().addAll(top, bottom, shoes);

        top.setOnAction(e -> handleCategoryFilter(top));
        bottom.setOnAction(e -> handleCategoryFilter(bottom));
        shoes.setOnAction(e -> handleCategoryFilter(shoes));

    }

    private void setupStyleDropdown() {
        CheckMenuItem formal = new CheckMenuItem("Formal");
        CheckMenuItem casual = new CheckMenuItem("Casual");
        CheckMenuItem others = new CheckMenuItem("Others");

        styleDropdown.getItems().addAll(formal, casual, others);

        formal.setOnAction(e -> handleStyleFilter(formal));
        casual.setOnAction(e -> handleStyleFilter(casual));
        others.setOnAction(e -> handleStyleFilter(others));
    }

    private void setupColorDropdown() {
        CheckMenuItem red = new CheckMenuItem("Red");
        CheckMenuItem orange = new CheckMenuItem("Orange");
        CheckMenuItem yellow = new CheckMenuItem("Yellow");
        CheckMenuItem green = new CheckMenuItem("Green");
        CheckMenuItem blue = new CheckMenuItem("Blue");
        CheckMenuItem violet = new CheckMenuItem("Violet");
        CheckMenuItem others = new CheckMenuItem("Others");

        colorDropdown.getItems().addAll(red, orange, yellow, green, blue, violet, others);

        red.setOnAction(e -> handleColorFilter(red));
        orange.setOnAction(e -> handleColorFilter(orange));
        yellow.setOnAction(e -> handleColorFilter(yellow));
        green.setOnAction(e -> handleColorFilter(green));
        blue.setOnAction(e -> handleColorFilter(blue));
        violet.setOnAction(e -> handleColorFilter(violet));
        others.setOnAction(e -> handleColorFilter(others));
    }

    private void handleCategoryFilter(CheckMenuItem item) {
        System.out.println("CATEGORY: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }

    private void handleStyleFilter(CheckMenuItem item) {
        System.out.println("STYLE: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }

    private void handleColorFilter(CheckMenuItem item) {
        System.out.println("COLOR: " + item.getText() + (item.isSelected() ? " selected" : " deselected"));
    }
    @FXML
    private void openUploadDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pilifitproject/view/UploadDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Piece");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

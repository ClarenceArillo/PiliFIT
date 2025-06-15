package com.example.pilifitproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class ContactController extends BaseController {

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        switchScene(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        switchScene(event, "AboutUs.fxml");
        System.out.println("AboutPage");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        switchScene(event, "ContactUs.fxml");
    }

}

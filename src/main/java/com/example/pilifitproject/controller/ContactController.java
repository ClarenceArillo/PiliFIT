package com.example.pilifitproject.controller;

import com.example.pilifitproject.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ContactController {

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "Home.fxml");
    }

    @FXML
    private void goAbout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "AboutUs.fxml");
        System.out.println("AboutPage");
    }

    @FXML
    private void goContact(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "ContactUs.fxml");
    }

}

package com.example.pilifitproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

    public static void switchTo(ActionEvent event, String fxmlFile) throws IOException {
        String fullPath = "/com/example/pilifitproject/view/" + fxmlFile;
        URL fxmlLocation = SceneSwitcher.class.getResource(fullPath);

        if (fxmlLocation == null) {
            throw new IOException("FXML file not found: " + fullPath);
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

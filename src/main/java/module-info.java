module com.example.pilifitproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.pilifitproject to javafx.fxml;
    exports com.example.pilifitproject;
    exports com.example.pilifitproject.controller;
    opens com.example.pilifitproject.controller to javafx.fxml;
}
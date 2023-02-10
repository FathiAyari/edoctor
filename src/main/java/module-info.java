module com.example.doctors {
    requires javafx.controls;
    requires javafx.fxml;


    requires java.sql;
    requires fontawesomefx;
    opens com.example.edoctors to javafx.fxml;
    exports com.example.edoctors;
    exports com.example.edoctors.models;
    exports com.example.edoctors.controllers;
    opens com.example.edoctors.controllers to javafx.fxml;
}
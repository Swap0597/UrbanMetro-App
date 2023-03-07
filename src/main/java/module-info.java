module com.example.urbanmetroapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kernel;
    requires layout;


    opens com.example.urbanmetroapp to javafx.fxml;
    exports com.example.urbanmetroapp;
}
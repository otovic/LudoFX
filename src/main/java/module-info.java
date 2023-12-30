module com.example.ludo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    exports com.example.ludo.utility;


    opens com.example.ludo to javafx.fxml;
    exports com.example.ludo;
}
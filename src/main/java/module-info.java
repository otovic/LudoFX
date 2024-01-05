module com.example.ludo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    exports com.example.ludo.utility;


    opens com.example.ludo to javafx.fxml;
    exports com.example.ludo;
    exports com.example.ludo.game;
    opens com.example.ludo.game to javafx.fxml;
}
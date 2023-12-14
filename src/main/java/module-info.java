module com.example.ludo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ludo to javafx.fxml;
    exports com.example.ludo;
}
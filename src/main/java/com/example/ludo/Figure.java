package com.example.ludo;

import javafx.scene.layout.BorderPane;

public class Figure {
    public BorderPane generateFigure() {
        BorderPane figure = new BorderPane();
        figure.setPrefSize(30, 30);
        figure.setStyle("-fx-background-color: #000000;");
        return figure;
    }
}

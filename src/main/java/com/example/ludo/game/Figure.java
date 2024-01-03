package com.example.ludo.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Figure {
    public String figureID;
    public String fieldID;
    public BorderPane figure;

    public Figure(String figureID, String fieldID) {
        this.figureID = figureID;
        this.fieldID = fieldID;
    }

    public String getHexColor() {
        switch (this.figureID.split("")[0]) {
            case "b":
                return "#639fff";
            case "y":
                return "#e7ff30";
            case "r":
                return "#e32a00";
            case "g":
                return "#42ff30";
            default:
                return "#000000";
        }
    }

    private String getImage() {
        switch (this.figureID.split("")[0]) {
            case "b":
                return "musk.png";
            case "y":
                return "wozniak.png";
            case "r":
                return "obama.png";
            case "g":
                return "sigal.png";
            default:
                return "musk.png";
        }
    }

    public int getIntColor() {
        return switch (this.figureID.split("")[0]) {
            case "y" -> 2;
            case "r" -> 3;
            case "g" -> 4;
            default -> 1;
        };
    }

    public String getColor() {
        return this.figureID.split("")[0];
    }

    public BorderPane generateFigure() {
        BorderPane figure = new BorderPane();
        figure.setPrefSize(35, 35);
        figure.setStyle("-fx-background-color: " + this.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 50;");
        Image image = new Image(this.getImage());
        ImageView imageView = new ImageView(image);
        figure.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        imageView.setFitHeight(35);
        imageView.setFitWidth(25);
        this.figure = figure;
        return figure;
    }
}

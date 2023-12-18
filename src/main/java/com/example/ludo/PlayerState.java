package com.example.ludo;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class PlayerState {
    public String username;
    public int color;
    public List<Figure> playerFigures;
    public VBox playerBox;

    public PlayerState(String username, List<Figure> playerFigures, int color) {
        this.username = username;
        this.playerFigures = playerFigures;
        this.color = color;
    }

    public void setPlayerBox(VBox playerBox) {
        this.playerBox = playerBox;
        playerBox.setAlignment(Pos.CENTER);
        HBox data = new HBox();
        data.setPrefWidth(200);
        data.setPrefHeight(100);
        data.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(80);
        stackPane.setPrefWidth(80);
        Image image = new Image("musk.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(60);
        stackPane.getChildren().addAll(imageView);

        VBox playerInfo = new VBox();
        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.setPrefWidth(100);
        playerInfo.setMaxHeight(80);
//        playerInfo.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");

        StackPane pname = new StackPane();
        pname.setPrefHeight(10);
        pname.setPrefWidth(100);
        pname.setAlignment(Pos.CENTER);
        Text username = new Text(this.username);
        username.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #000000;");
        pname.getChildren().addAll(username);

        playerInfo.getChildren().addAll(pname);

        VBox inHouse = new VBox();
        inHouse.setAlignment(Pos.CENTER);
        inHouse.setPrefHeight(55);
        inHouse.setPrefWidth(100);
//        inHouse.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");

        HBox firstHouseRow = new HBox();
        firstHouseRow.setAlignment(Pos.CENTER);
        firstHouseRow.setPrefWidth(100);
        firstHouseRow.setPrefHeight(25);
//        firstHouseRow.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        firstHouseRow.setSpacing(20);
        firstHouseRow.getChildren().addAll(this.createBoxy(), this.createBoxy());

        HBox secondHouseRow = new HBox();
        secondHouseRow.setAlignment(Pos.CENTER);
        secondHouseRow.setPrefWidth(100);
        secondHouseRow.setPrefHeight(25);
//        secondHouseRow.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        secondHouseRow.setSpacing(20);
        secondHouseRow.getChildren().addAll(this.createBoxy(), this.createBoxy());

        inHouse.getChildren().addAll(firstHouseRow, secondHouseRow);
        playerInfo.getChildren().addAll(inHouse);

        data.getChildren().addAll(stackPane, playerInfo);
        playerBox.getChildren().addAll(data);
    }

    public StackPane createBoxy() {
        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(20);
        stackPane.setPrefWidth(20);
        stackPane.setStyle("-fx-background-color: white; -fx-background-radius: 50; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 50;");
        return stackPane;
    }
}

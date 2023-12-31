package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuScreen {
    public static void initMainMenuScreen(final Session session) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Button host = new Button("Host Game");
        host.setPrefHeight(30);
        host.setPrefWidth(250);

        Button join = new Button("Join Game");
        join.setPrefHeight(30);
        join.setPrefWidth(250);

        Button logout = new Button("Logout");
        logout.setPrefHeight(30);
        logout.setPrefWidth(250);
        logout.setOnMouseClicked(e -> {
            session.logout();
            StartingScreen.initStartingScreen(session);
        });

        container.getChildren().addAll(host, join, logout);

        session.screenController.init(new Scene(container, 300, 300), "Main Menu");
    }
}

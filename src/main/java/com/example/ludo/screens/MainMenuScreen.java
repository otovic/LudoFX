package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class MainMenuScreen {
    public static void initMainMenuScreen(final NewScreenCallback screen) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        screen.run(new Scene(container, 300, 300), "Main Menu");
    }
}

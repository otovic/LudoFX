package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartingScreen {
    public Session session;
    public RegisterScreen registerScreen = null;

    public StartingScreen(Session session) {
        this.session = session;
        this.registerScreen = new RegisterScreen(session);
    }

    public void initStartingScreen(final NewScreenCallback screen) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Button logIn = new Button("Log In");
        logIn.setPrefHeight(30);
        logIn.setPrefWidth(250);

        Button register = new Button("Register");
        register.setPrefHeight(30);
        register.setPrefWidth(250);
        register.setOnMouseClicked(e -> {
            registerScreen.initRegisterScreen(screen);
        });

        container.getChildren().addAll(logIn, register);

        screen.run(new Scene(container, 300, 300), "Ludo");
    }
}

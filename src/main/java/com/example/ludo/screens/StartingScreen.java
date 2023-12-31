package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import com.example.ludo.utility.ScreenController;
import com.example.ludo.utility.Utility;
import com.example.ludo.utility.UtilityFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartingScreen {
    public static void initStartingScreen(final Session session) {
        RegisterScreen registerScreen = null;
        String[] server = new String[1];
        server[0] = session.getServer();
        boolean[] serverSet = new boolean[1];
        serverSet[0] = false;

        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Label error = new Label();
        error.setVisible(false);

        Button logIn = new Button("Log In");
        logIn.setPrefHeight(30);
        logIn.setPrefWidth(250);
        logIn.setOnMouseClicked(e -> {
            if (server[0] == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill the server field");
                return;
            }

            if (server[0].equals("")) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill the server field");
                return;
            }

            if (!serverSet[0]) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Invalid server address");
                return;
            }

            LogInScreen.initLoginScreen(session);
        });

        Button register = new Button("Register");
        register.setPrefHeight(30);
        register.setPrefWidth(250);
        register.setOnMouseClicked(e -> {
            if (server[0] == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill the server field");
                return;
            }

            if (server[0].equals("")) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill the server field");
                return;
            }

            if (!serverSet[0]) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Invalid server address");
                return;
            }

            RegisterScreen.initRegisterScreen(session);
        });


        container.getChildren().addAll(logIn, register, UtilityFX.createInputSet("Server: ", (data) -> {
            server[0] = data;
            serverSet[0] = session.setServer(data);
        }, false, "localhost:8082 or 192.168.1.12:8082", session.getServer()), error);

        session.screenController.init(new Scene(container, 300, 300), "Ludo");
    }
}

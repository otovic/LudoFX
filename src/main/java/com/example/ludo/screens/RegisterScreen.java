package com.example.ludo.screens;

import com.example.ludo.Player;
import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import com.example.ludo.utility.*;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class RegisterScreen {

    public static void initRegisterScreen(final Session session) {
        final String[] username = new String[1];
        final String[] email = new String[1];
        final String[] password = new String[1];
        final String[] confirmPassword = new String[1];

        VBox container = new VBox();
        container.setPrefHeight(400);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Button register = new Button("Register");
        register.setPrefHeight(30);
        register.setPrefWidth(250);

        Label error = new Label();
        error.setVisible(false);

        Button back = new Button("Back");
        back.setPrefHeight(30);
        back.setPrefWidth(250);
        back.setOnMouseClicked(e -> {
            StartingScreen.initStartingScreen(session);
        });

        register.setOnMouseClicked(e -> {
            error.setVisible(false);
            if (username[0] == null || email[0] == null || password[0] == null || confirmPassword[0] == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill all the fields");
                return;
            }

            if (!password[0].equals(confirmPassword[0])) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Passwords do not match");
                return;
            }

            if (!Utility.isEmailValid(email[0])) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please enter a valid email");
                return;
            }

            session.setTask(() -> {
                EventResponse data = new EventResponse("register", new HashMap<>() {{
                }}, new HashMap<>() {{
                    put("username", username[0]);
                    put("email", email[0]);
                    put("password", password[0]);
                    put("confirmPassword", confirmPassword[0]);
                }});

                Listener listener = new Listener("registerScreen", (res) -> {
                    Platform.runLater(() -> {
                        EventResponse response = new Gson().fromJson(res, EventResponse.class);
                        if (response.eventData.get("error") != null) {
                            String errorMessage = response.eventData.get("error");
                            error.setVisible(true);
                            error.setStyle("-fx-text-fill: red;");
                            error.setText(Utility.getErrorMessage(errorMessage));
                            session.removeListener("registerScreen");
                        } else {
                            Player player = new Player(response.eventData.get("key"), username[0], email[0]);
                            session.setControlledPlayer(player);
                            session.removeListener("registerScreen");
                            MainMenuScreen.initMainMenuScreen(session);
                        }
                    });
                });

                session.addListener(listener);
                session.establishConnection(data, "register", (err) -> {
                    Platform.runLater(() -> {
                        error.setVisible(true);
                        error.setStyle("-fx-text-fill: red;");
                        error.setText(err);
                        session.removeListener("registerScreen");
                    });
                });
            });
        });

        container.getChildren().addAll(UtilityFX.createInputSet("Username", (value) -> {
            username[0] = value;
        }, false, "username", null), UtilityFX.createInputSet("Email", (value) -> {
            email[0] = value;
        }, false, "email", null), UtilityFX.createInputSet("Password", (value) -> {
            password[0] = value;
        }, true, "password", null), UtilityFX.createInputSet("Confirm Password", (value) -> {
            confirmPassword[0] = value;
        }, true, "password", null), register, error, back);

        session.screenController.init(new Scene(container, 300, 400), "Ludo");
    }
}

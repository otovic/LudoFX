package com.example.ludo.screens;

import com.example.ludo.game.Figure;
import com.example.ludo.game.Player;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.example.ludo.utility.Utility;
import com.example.ludo.utility.UtilityFX;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.HashMap;

public class LogInScreen {
    public static void initLoginScreen(final Session session) {
        final String[] username = new String[1];
        final String[] password = new String[1];

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
            if (username[0] == null || password[0] == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill all the fields");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
                writer.write(username[0] + " " + password[0]);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            session.setTask(() -> {
                EventResponse response = new EventResponse("login", new HashMap<>() {{

                }}, new HashMap<>() {{
                    put("username", username[0]);
                    put("password", password[0]);
                }});

                Listener listener = new Listener("login", (eventResponse) -> {
                    Platform.runLater(() -> {
                        EventResponse serverResponse = new Gson().fromJson(eventResponse, EventResponse.class);
                        if (serverResponse.eventData.get("error") != null) {
                            error.setVisible(true);
                            error.setStyle("-fx-text-fill: red;");
                            error.setText(Utility.getErrorMessage(serverResponse.eventData.get("error")));
                            session.removeListener("login");
                            return;
                        }
                        Player player = new Player(serverResponse.eventData.get("key"), username[0], null);
                        session.setControlledPlayer(player);
                        session.removeListener("login");
                        MainMenuScreen.initMainMenuScreen(session);
                    });
                });

                session.addListener(listener);
                session.establishConnection(response, "login", (err) -> {
                    Platform.runLater(() -> {
                        error.setVisible(true);
                        error.setStyle("-fx-text-fill: red;");
                        error.setText(Utility.getErrorMessage(err));
                        session.removeListener("login");
                        return;
                    });
                });
            });
        });

        Button back = new Button("Back");
        back.setPrefHeight(30);
        back.setPrefWidth(250);
        back.setOnMouseClicked(e -> {
            StartingScreen.initStartingScreen(session);
        });

        if (!new File("credentials.txt").exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
                writer.write("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                String[] credentials = line.split(" ");
                username[0] = credentials[0];
                password[0] = credentials[1];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        container.getChildren().addAll(UtilityFX.createInputSet("Username", (String usrnm) -> {
            username[0] = usrnm;
        }, false, "Username", username[0] != null ? username[0] : null), UtilityFX.createInputSet("Password", (String pass) -> {
            password[0] = pass;
        }, true, "Password", password[0] != null ? password[0] : null), logIn, back, error);

        session.screenController.init(new Scene(container, 300, 300), "Log In");
    }
}

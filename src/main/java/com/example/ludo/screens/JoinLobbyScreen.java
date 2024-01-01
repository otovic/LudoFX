package com.example.ludo.screens;

import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class JoinLobbyScreen {
    public static void init(final Session session) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setMaxWidth(400);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        container.setSpacing(10);

        ListView<String> lobbies = new ListView<>();
        lobbies.setPrefHeight(300);
        lobbies.setMaxWidth(350);

        Button join = new Button("Join");
        join.setPrefHeight(30);
        join.setPrefWidth(250);

        Button back = new Button("Back");
        back.setPrefHeight(30);
        back.setPrefWidth(250);
        back.setOnMouseClicked(e -> {
            MainMenuScreen.initMainMenuScreen(session);
        });

        Listener listener = new Listener("joinlobby", (eventResponse) -> {
            Platform.runLater(() -> {
                EventResponse response = new Gson().fromJson(eventResponse, EventResponse.class);
                if (response.eventName.equals("fetchLobbies")) {
                    System.out.println(response.eventData.size());
                    for (int i = 0; i < response.eventData.size(); i++) {
                        lobbies.getItems().add(response.eventData.get("lobby" + i));
                    }
                }
            });
        });

        session.addListener(listener);
        session.executeEvent(new EventResponse("fetchLobbies", new HashMap<>(), new HashMap<>()));

        container.getChildren().addAll(lobbies, join, back);
        session.screenController.init(new Scene(container, 400, 350), "Join Lobby");
    }
}
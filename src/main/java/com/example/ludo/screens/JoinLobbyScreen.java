package com.example.ludo.screens;

import com.example.ludo.game.GameMode;
import com.example.ludo.game.Player;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class JoinLobbyScreen {
    public static void init(final Session session) {
        final String[] selectedLobby = new String[1];
        selectedLobby[0] = "";

        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setMaxWidth(400);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        container.setSpacing(10);

        ListView<String> lobbies = new ListView<>();
        lobbies.setPrefHeight(300);
        lobbies.setMaxWidth(350);
        lobbies.setOnMouseClicked(e -> {
            selectedLobby[0] = lobbies.getSelectionModel().getSelectedItem().split("'")[0];
        });

        Button join = new Button("Join");
        join.setPrefHeight(30);
        join.setPrefWidth(250);
        join.setOnMouseClicked(e -> {
            session.executeEvent(new EventResponse("joinLobby", new HashMap<>() {{put("lobbyID", selectedLobby[0]);}}, new HashMap<>() {{}}));
        });

        Label error = new Label();
        error.setPrefWidth(250);

        Button back = new Button("Back");
        back.setPrefHeight(30);
        back.setPrefWidth(250);
        back.setOnMouseClicked(e -> {
            session.gameMode = null;
            session.removeListener("joinlobby");
            MainMenuScreen.initMainMenuScreen(session);
        });

        Listener listener = new Listener("joinlobby", (eventResponse) -> {
            Platform.runLater(() -> {
                EventResponse response = new Gson().fromJson(eventResponse, EventResponse.class);
                if (session.gameMode == null) {
                    session.gameMode = new GameMode(null, null, session.player);
                }
                System.out.println(response.eventName);
                error.setText("");
                container.getChildren().remove(error);
                if (response.eventName.equals("fetchLobbies")) {
                    for (int i = 0; i < response.eventData.size(); i++) {
                        lobbies.getItems().add(response.eventData.get("lobby" + i) + "'s lobby");
                    }
                }
                if (response.eventName.equals("syncPlayer")) {
                    System.out.println("Synced: " + response.eventData.get("username"));
                    Player p = new Player(response.eventData.get("key"), response.eventData.get("username"));
                    p.setReady(response.eventData.get("ready"));
                    session.gameMode.playerJoined(p.key, p);
                }
                if (response.eventName.equals("joinLobby")) {
                    session.gameMode.key = response.eventData.get("lobbyID");
                    session.gameMode.ownerID = response.eventData.get("ownerID");
                    session.removeListener("joinlobby");
                    LobbyScreen.init(session);
                }
                if (response.eventName.equals("lobbyNotFound")) {
                    error.setText("Lobby not found");
                    error.setStyle("-fx-text-fill: red;");
                    container.getChildren().add(error);
                }
            });
        });

        session.addListener(listener);
        session.executeEvent(new EventResponse("fetchLobbies", new HashMap<>(), new HashMap<>()));

        container.getChildren().addAll(lobbies, join, back);
        session.screenController.init(new Scene(container, 400, 350), "Join Lobby");
    }
}
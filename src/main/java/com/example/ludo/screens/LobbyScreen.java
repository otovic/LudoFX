package com.example.ludo.screens;

import com.example.ludo.Player;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.example.ludo.utility.UtilityFX;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LobbyScreen {
    public static void init(final Session session) {
        VBox container = new VBox();
        container.setPrefHeight(500);
        container.setPrefWidth(720);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        HBox containerH = new HBox();
        containerH.setPrefHeight(500);
        containerH.setPrefWidth(500);
        containerH.setAlignment(Pos.CENTER);
        containerH.setSpacing(10);
        containerH.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        Label playerTitle = new Label("Players");

        VBox leftContainer = new VBox();
        leftContainer.setPrefHeight(250);
        leftContainer.setPrefWidth(250);
        leftContainer.setAlignment(Pos.CENTER_LEFT);
        leftContainer.setSpacing(10);

        ListView<String> players = new ListView<>();
        players.setPrefHeight(300);
        players.setPrefWidth(250);
        players.getItems().add(session.player.username);

        Label messagesTitle = new Label("Messages");

        VBox rightContainer = new VBox();
        rightContainer.setPrefHeight(250);
        rightContainer.setPrefWidth(250);
        rightContainer.setAlignment(Pos.CENTER_LEFT);
        rightContainer.setSpacing(10);

        ScrollPane messages = new ScrollPane();
        messages.setPrefHeight(250);
        messages.setPrefWidth(250);
        messages.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        HBox sendContainer = new HBox();
        sendContainer.setPrefHeight(50);
        sendContainer.setPrefWidth(250);
        sendContainer.setAlignment(Pos.CENTER);

        TextField messageInput = new TextField();
        messageInput.setPrefHeight(30);
        messageInput.setPrefWidth(200);

        VBox messageContainer = new VBox();
        messageContainer.setPrefWidth(200);
        messageContainer.setSpacing(10);

        Button send = new Button(">");
        send.setPrefHeight(30);
        send.setPrefWidth(50);
        send.setOnMouseClicked((e) -> {
            Platform.runLater(() -> {
                messageContainer.getChildren().add(UtilityFX.createMessage(messageInput.getText(), "pera", true));
                messageInput.setText("");
                messages.setContent(messageContainer);
            });

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                Platform.runLater(() -> {
                    messages.setVvalue(1.0);
                });
            }, 100, TimeUnit.MILLISECONDS);
        });

        sendContainer.getChildren().addAll(messageInput, send);

        VBox buttons = new VBox();
        buttons.setPrefHeight(250);
        buttons.setPrefWidth(500);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        Button start = new Button("Start");
        start.setPrefHeight(30);
        start.setPrefWidth(250);
//        if (session.gameMode.ownerID.equals(session.player.key)) {
//            if (session.gameMode.players.size() > 1) {
//                if (session.gameMode.arePlayersReady()) {
//                    start.setDisable(false);
//                } else {
//                    start.setDisable(true);
//                }
//            } else {
//                start.setDisable(true);
//            }
//        } else {
//            start.setDisable(true);
//        }

        Button ready = new Button("Ready");
        ready.setPrefHeight(30);
        ready.setPrefWidth(250);

        Button leave = new Button("Leave");
        leave.setPrefHeight(30);
        leave.setPrefWidth(250);
        leave.setOnMouseClicked(e -> {
            session.removeListener("lobby");
            EventResponse response = new EventResponse("leaveLobby", new HashMap<>() {{}}, new HashMap<>() {{}});
            session.executeEvent(response);
            session.cancelLobby();
            MainMenuScreen.initMainMenuScreen(session);
        });

        buttons.getChildren().addAll(start, ready, leave);

        Listener listener = new Listener("lobby", (eventResponse) -> {
            EventResponse response = new Gson().fromJson(eventResponse, EventResponse.class);
            if (response.eventName.equals("lobbyDeleted")) {
                session.removeListener("lobby");
                session.cancelLobby();
                MainMenuScreen.initMainMenuScreen(session);
            }
            if (response.eventName.equals("playerLeftLobby")) {
                session.gameMode.playerLeft(response.eventData.get("playerID"));
            }
        });

        leftContainer.getChildren().addAll(playerTitle, players);
        rightContainer.getChildren().addAll(messagesTitle, messages, sendContainer);
        containerH.getChildren().addAll(leftContainer, rightContainer);
        container.getChildren().addAll(containerH, buttons);
        session.addListener(listener);
        session.screenController.init(new Scene(container, 500, 550), "Lobby");
    }

}

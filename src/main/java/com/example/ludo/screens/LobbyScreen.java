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
        session.gameMode.players.forEach((key, player) -> {
            players.getItems().add(player.username + " | " + (player.isReady ? "Ready" : "Not Ready"));
        });

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
                EventResponse response = new EventResponse("sendMessage", new HashMap<>() {{
                    put("message", messageInput.getText());
                    put("lobbyID", session.gameMode.key);
                }}, new HashMap<>() {{}});
                messageContainer.getChildren().add(UtilityFX.createMessage(messageInput.getText(), session.player.username, true));
                messageInput.setText("");
                messages.setContent(messageContainer);
                session.executeEvent(response);
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
        if (session.gameMode.ownerID.equals(session.player.key)) {
            buttons.getChildren().add(start);
        }

        checkCanStart(session, buttons, start);

        Button ready = new Button("Ready");
        ready.setPrefHeight(30);
        ready.setPrefWidth(250);
        ready.setOnMouseClicked(e -> {
            session.executeEvent(new EventResponse("ready", new HashMap<>() {{
                put("lobbyID", session.gameMode.key);
            }}, new HashMap<>() {{}}));
            if (session.gameMode.players.get(session.player.key).isReady) {
                session.gameMode.playerUnready(session.player.key);
                players.getItems().remove(session.player.username + " | Ready");
                players.getItems().add(session.player.username + " | Not Ready");
            } else {
                session.gameMode.playerReady(session.player.key);
                players.getItems().remove(session.player.username + " | Not Ready");
                players.getItems().add(session.player.username + " | Ready");
            }
            checkCanStart(session, buttons, start);
        });

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

        buttons.getChildren().addAll(ready, leave);

        Listener listener = new Listener("lobby", (eventResponse) -> {
            Platform.runLater(() -> {
                EventResponse response = new Gson().fromJson(eventResponse, EventResponse.class);
                if (response.eventName.equals("lobbyDeleted")) {
                    session.removeListener("lobby");
                    session.cancelLobby();
                    MainMenuScreen.initMainMenuScreen(session);
                }
                if (response.eventName.equals("playerLeftLobby")) {
                    session.gameMode.playerLeft(response.eventData.get("playerID"));
                    players.getItems().remove(response.eventData.get("username") + " | Ready");
                    players.getItems().remove(response.eventData.get("username") + " | Not Ready");
                }
                if (response.eventName.equals("playerJoinedLobby")) {
                    Player p = new Player(response.eventData.get("playerID"), response.eventData.get("username"));
                    session.gameMode.playerJoined(p.key, p);
                    players.getItems().add(p.username + " | Not Ready");
                }
                if (response.eventName.equals("receiveMessage")) {
                    messageContainer.getChildren().add(UtilityFX.createMessage(response.eventData.get("message"), response.eventData.get("username"), false));
                    messages.setContent(messageContainer);
                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    executor.schedule(() -> {
                        Platform.runLater(() -> {
                            messages.setVvalue(1.0);
                        });
                    }, 100, TimeUnit.MILLISECONDS);
                }
                if (response.eventName.equals("playerReady")) {
                    String playerID = response.eventData.get("playerID");
                    System.out.println(playerID);
                    if (session.gameMode.players.get(playerID).isReady) {
                        session.gameMode.playerUnready(playerID);
                        String playerUsername = session.gameMode.players.get(playerID).username;
                        int position = players.getItems().indexOf(playerUsername + " | Ready");
                        players.getItems().remove(playerUsername + " | Ready");
                        players.getItems().add(position, playerUsername + " | Not Ready");
                    } else {
                        session.gameMode.playerReady(playerID);
                        String playerUsername = session.gameMode.players.get(playerID).username;
                        int position = players.getItems().indexOf(playerUsername + " | Not Ready");
                        players.getItems().remove(playerUsername + " | Not Ready");
                        players.getItems().add(position, playerUsername + " | Ready");
                    }
                    checkCanStart(session, buttons, start);
                }
            });
        });

        leftContainer.getChildren().addAll(playerTitle, players);
        rightContainer.getChildren().addAll(messagesTitle, messages, sendContainer);
        containerH.getChildren().addAll(leftContainer, rightContainer);
        container.getChildren().addAll(containerH, buttons);
        session.addListener(listener);
        session.screenController.init(new Scene(container, 500, 550), "Lobby");
    }

    private static void checkCanStart(Session session, VBox buttons, Button start) {
        if (session.gameMode.ownerID.equals(session.player.key)) {
            if (session.gameMode.players.size() > 1) {
                if (session.gameMode.arePlayersReady()) {
                    start.setDisable(false);
                } else {
                    start.setDisable(true);
                }
            } else {
                start.setDisable(true);
            }
        } else {
            start.setDisable(true);
        }
    }

}

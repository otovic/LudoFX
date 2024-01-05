package com.example.ludo.screens;

import com.example.ludo.game.Figure;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.example.ludo.utility.LudoFX;
import com.example.ludo.utility.UtilityFX;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameScreen {
    public static void init(final Session session) {
        BorderPane v = new BorderPane();
        v.setPrefHeight(780);
        v.setPrefWidth(1300);
        v.setStyle("-fx-background-color: #ffffff;");

        HBox top = new HBox();
        top.setPrefHeight(120);
        top.setPrefWidth(1300);

        VBox player1 = new VBox();
        player1.setMaxHeight(100);
        player1.setPrefWidth(200);
        player1.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black black; -fx-border-width: 3");

        VBox middle = new VBox();
        middle.setMaxHeight(100);
        middle.setPrefWidth(850);

        VBox player2 = new VBox();
        player2.setMaxHeight(100);
        player2.setPrefWidth(200);
        player2.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black black; -fx-border-width: 3");

        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        top.getChildren().addAll(player1, middle, player2);

        HBox boardContainer = new HBox();
        boardContainer.getChildren().addAll(LudoFX.createBoard(session.gameMode));

        VBox chatContainer = new VBox();
        chatContainer.setPrefHeight(540);
        chatContainer.setPrefWidth(220);
        chatContainer.setAlignment(Pos.CENTER);
        chatContainer.setPadding(new javafx.geometry.Insets(10, 0, 10, 10));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(450);
        scrollPane.setPrefWidth(350);

        VBox msgContainer = new VBox();
        msgContainer.setPrefHeight(445);
        msgContainer.setPrefWidth(195);
        msgContainer.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        msgContainer.setSpacing(5);

        HBox sender = new HBox();
        sender.setPrefHeight(50);
        sender.setPrefWidth(350);
        sender.setAlignment(Pos.CENTER);

        final String[] msg = new String[1];
        TextField message = new TextField();
        message.setPrefHeight(30);
        message.setPrefWidth(250);
        message.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                msg[0] = message.getText();
            }
        });

        Button send = new Button(">");
        send.setPrefHeight(30);
        send.setPrefWidth(100);
        send.setOnMouseClicked(e -> {
            if (msg[0] != null) {
                session.executeEvent(new EventResponse("sendMessage", new HashMap<>() {{
                    put("message", msg[0]);
                    put("lobbyID", session.gameMode.key);
                }}, new HashMap<>() {{}}));
                msgContainer.getChildren().add(UtilityFX.createMessage(msg[0], session.player.username, true));
                scrollPane.setContent(msgContainer);
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> {
                    Platform.runLater(() -> {
                        scrollPane.setVvalue(1.0);
                    });
                }, 100, TimeUnit.MILLISECONDS);
                msg[0] = null;
                message.setText("");
            }
        });

        sender.getChildren().addAll(message, send);

        scrollPane.setContent(msgContainer);
        chatContainer.getChildren().addAll(scrollPane, sender);

        VBox scoreBoards = new VBox();
        scoreBoards.setPrefHeight(540);
        scoreBoards.setPrefWidth(220);

        HBox butum = new HBox();
        butum.setPrefHeight(120);
        butum.setPrefWidth(1300);

        VBox player4 = new VBox();
        player4.setMaxHeight(100);
        player4.setPrefWidth(200);
        player4.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black black; -fx-border-width: 3");

        VBox midBot = new VBox();
        midBot.setMaxHeight(100);
        midBot.setPrefWidth(850);

        VBox player3 = new VBox();
        player3.setMaxHeight(100);
        player3.setPrefWidth(200);
        player3.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black black; -fx-border-width: 3");

        butum.setAlignment(Pos.CENTER);
        butum.setSpacing(10);
        butum.getChildren().addAll(player4, midBot, player3);

        v.setTop(top);
        v.setLeft(chatContainer);
        v.setRight(scoreBoards);
        v.setCenter(boardContainer);
        v.setBottom(butum);

        session.gameMode.players.forEach((key, value) -> {
            value.setGameMode(session.gameMode, session);
            switch (value.color) {
                case 1:
                    value.setPlayerBox(player1);
                    session.gameMode.initPlayer(key);
                    break;
                case 2:
                    value.setPlayerBox(player2);
                    session.gameMode.initPlayer(key);
                    break;
                case 3:
                    value.setPlayerBox(player3);
                    session.gameMode.initPlayer(key);
                    break;
                case 4:
                    value.setPlayerBox(player4);
                    session.gameMode.initPlayer(key);
                    break;
            }
        });

        Listener listener = new Listener("game", (eventResponse) -> {
            Platform.runLater(() -> {
                EventResponse response = new Gson().fromJson(eventResponse, EventResponse.class);
                if (response.eventName.equals("initGame")) {
                    session.gameMode.start();
                }
                if (response.eventName.equals("rolledDice")) {
                    session.gameMode.players.get(response.eventData.get("playerID")).roll(response.eventData.get("diceValue"));
                }
                if (response.eventName.equals("initShowMoves")) {
                    String playerID = response.eventData.get("playerID");
                    String diceValue = response.eventData.get("diceValue");
                    System.out.println("Player " + playerID + " rolled " + diceValue);
                    session.gameMode.players.get(playerID).showPossibleMoves(playerID, diceValue);
                }
                if (response.eventName.equals("initNewPlayerTurn")) {
                    System.out.println("New player turn");
                    System.out.println(response.eventData.get("diceValue"));
                    session.gameMode.newPlayerTurn(response.eventData.get("diceValue"));
                }
                if (response.eventName.equals("moveFigure")) {
                    final String playerID = response.eventData.get("playerID");
                    final String figureID = response.eventData.get("figure");
                    final String rolled = response.eventData.get("rolled");

                    Figure f = session.gameMode.players.get(playerID).playerFigures.stream().filter(figure -> figure.figureID.equals(figureID)).findFirst().orElse(null);

                    session.gameMode.players.get(playerID).moveFigure(rolled, f);
                }
                if (response.eventName.equals("receiveMessage")) {
                    String recMsg = response.eventData.get("message");
                    String username = response.eventData.get("username");

                    msgContainer.getChildren().add(UtilityFX.createMessage(recMsg, username, false));
                    scrollPane.setContent(msgContainer);
                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    executor.schedule(() -> {
                        Platform.runLater(() -> {
                            scrollPane.setVvalue(1.0);
                        });
                    }, 100, TimeUnit.MILLISECONDS);
                }
                if (response.eventName.equals("lobbyDeleted") || response.eventName.equals("playerLeftLobby")) {
                    String playerID = response.eventData.get("playerID");
                    session.gameMode.playerLeftGame(playerID, session);
                }
            });
        });

        session.executeEvent(new EventResponse("syncStartGame", new HashMap<>() {{
            put("lobbyID", session.gameMode.key);
        }}, new HashMap<>() {{}}));

        session.addListener(listener);
        session.screenController.init(new Scene(v, 1300, 780), "Ludo");
    }
}

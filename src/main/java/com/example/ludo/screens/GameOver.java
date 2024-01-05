package com.example.ludo.screens;

import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameOver {
    public static void init(final Session session, final String winner) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);

        Text text = new Text(winner + " won the game!");

        container.getChildren().add(text);

        session.gameMode.players.forEach((key, player) -> {
            if (player.isReady) {
                player.isReady = false;
            }
        });

        Listener listener = new Listener("gameover", (eventResponse) -> {
            Platform.runLater(() -> {
                EventResponse serverResponse = new Gson().fromJson(eventResponse, EventResponse.class);
                if (serverResponse.eventData.get("error") != null) {
                    session.removeListener("gameover");
                    System.out.println("error");
                    return;
                }
                if (serverResponse.eventName.equals("playerLeftLobby")) {
                    String playerID = serverResponse.eventData.get("playerID");
                    session.gameMode.playerLeft(playerID);
                }
                if (serverResponse.eventName.equals("lobbyDeleted")) {
                    session.removeListener("gameover");

                    MainMenuScreen.initMainMenuScreen(session);
                }
                session.removeListener("gameover");
                session.initLobby(serverResponse.eventData.get("lobbyID"), session.player);
                LobbyScreen.init(session);
            });
        });

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                Platform.runLater(() -> {
                    session.removeListener("gameover");
                    System.out.println("gameoverRRR");
                    LobbyScreen.init(session);
                });
            }, 5, TimeUnit.SECONDS);

        session.addListener(listener);
        session.screenController.init(new Scene(container, 300, 300), "Game Over");
    }
}

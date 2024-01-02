package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class MainMenuScreen {
    public static void initMainMenuScreen(final Session session) {
        VBox container = new VBox();
        container.setPrefHeight(300);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Label player = new Label("Welcome " + session.player.username);

        Button host = new Button("Host Game");
        host.setPrefHeight(30);
        host.setPrefWidth(250);
        host.setOnMouseClicked(e -> {
            session.setTask(() -> {
                EventResponse response = new EventResponse("createLobby", new HashMap<>() {{}}, new HashMap<>() {{}});

                Listener listener = new Listener("mainmenu", (eventResponse) -> {
                    Platform.runLater(() -> {
                        EventResponse serverResponse = new Gson().fromJson(eventResponse, EventResponse.class);
                        if (serverResponse.eventData.get("error") != null) {
                            session.removeListener("mainmenu");
                            System.out.println("error");
                            return;
                        }
                        System.out.println(serverResponse.eventData.get("lobbyID"));
                        session.removeListener("mainmenu");
                        session.initLobby(serverResponse.eventData.get("lobbyID"), session.player);
                        LobbyScreen.init(session);
                    });
                });

                session.addListener(listener);
                session.executeEvent(response);
            });
        });

        Button join = new Button("Join Game");
        join.setPrefHeight(30);
        join.setPrefWidth(250);
        join.setOnMouseClicked(e -> {
//            session.setTask(() -> {
//                EventResponse response = new EventResponse("getLobbies", new HashMap<>() {{}}, new HashMap<>() {{}});
//
//                Listener listener = new Listener("mainmenu", (eventResponse) -> {
//                    Platform.runLater(() -> {
//                        EventResponse serverResponse = new Gson().fromJson(eventResponse, EventResponse.class);
//                        if (serverResponse.eventData.get("error") != null) {
//                            session.removeListener("mainmenu");
//                            System.out.println("error");
//                            return;
//                        }
//
//                        session.removeListener("mainmenu");
//                        JoinLobbyScreen.init(session);
//                    });
//                });
//
//                session.addListener(listener);
//                session.executeEvent(response);
//            });
            JoinLobbyScreen.init(session);
        });

        Button logout = new Button("Logout");
        logout.setPrefHeight(30);
        logout.setPrefWidth(250);
        logout.setOnMouseClicked(e -> {
            session.logout();
            StartingScreen.initStartingScreen(session);
        });

        container.getChildren().addAll(player, host, join, logout);

        session.screenController.init(new Scene(container, 300, 300), "Main Menu");
    }
}

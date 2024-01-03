package com.example.ludo;

import com.example.ludo.game.GameMode;
import com.example.ludo.screens.StartingScreen;
import com.example.ludo.session.Session;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public Session session = new Session();
    public GameMode gameMode;
    @Override
    public void start(Stage stage) throws IOException {
        this.session.screenController.setStage(stage);
//        LobbyScreen.init(this.session);
        StartingScreen.initStartingScreen(this.session);
//        JoinLobbyScreen.init(this.session);
    }

    public static void main(String[] args) {
        launch();
    }
}
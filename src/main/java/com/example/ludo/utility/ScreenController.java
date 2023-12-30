package com.example.ludo.utility;

import com.example.ludo.screens.StartingScreen;
import com.example.ludo.session.Session;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenController {
    public Stage stage;
    public Scene screen;
    public Session session = new Session();
    public StartingScreen startingScreen = new StartingScreen(session);

    public ScreenController() {}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitle(String title) {
        this.stage.setTitle(title);
    }

    public void init() {
        startingScreen.initStartingScreen((push, title) -> {
            this.screen = push;
            stage.setScene(screen);
            this.setTitle(title);
            stage.show();
        });
    }
}

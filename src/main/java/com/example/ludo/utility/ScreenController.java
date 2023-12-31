package com.example.ludo.utility;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.screens.StartingScreen;
import com.example.ludo.session.Session;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenController {
    public Stage stage;
    public Scene screen;
    public Scene currentScreen = null;

    public ScreenController() {}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitle(String title) {
        this.stage.setTitle(title);
    }

    public void init(final Scene screen, final String title) {
        this.screen = screen;
        this.stage.setScene(screen);
        this.setTitle(title);
        this.stage.show();
    }
}

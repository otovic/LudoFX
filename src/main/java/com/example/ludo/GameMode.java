package com.example.ludo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameMode {
    public String key;
    public String ownerID;
    public LudoGameState state;
    public int pos = 1;

    public GameMode(String key, String ownerID, LudoGameState state) {
        this.key = key;
        this.ownerID = ownerID;
        this.state = state;
    }

    public void addField(final String id, final Player player, final Pane field) {
        this.state.addField(id, player, field);
    }

    public void printState() {
        System.out.println(this.state.fields);
    }

    public void start() {
        this.state.start();
    }

//    public void addFigure() {
//        this.state.fields.get("1").field.getChildren().add(new Figure().generateFigure());
//    }

    public void moveFigure() {
//        Platform.runLater(() -> {
//            Timeline timeline = new Timeline();
//            for (int i = 1; i < 40; i++) {
//                        Duration duration = Duration.millis(500 * i);
//                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
//                            this.state.fields.get(String.valueOf(pos)).field.getChildren().remove(0);
//                            this.state.fields.get(String.valueOf(pos + 1)).field.getChildren().add(new Figure().generateFigure());
//                            this.pos++;
//                        });
//                timeline.getKeyFrames().add(keyFrame);
//            }
//            timeline.play();
//        });
    }
}

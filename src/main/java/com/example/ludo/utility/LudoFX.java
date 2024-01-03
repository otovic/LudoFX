package com.example.ludo.utility;

import com.example.ludo.game.GameMode;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LudoFX {
    public static VBox createBoard(final GameMode gameMode) {
        VBox board = new VBox();
        board.setPrefWidth(900);
        board.setAlignment(Pos.CENTER);
        board.setSpacing(5);
        board.getChildren().addAll(
                createPattern(gameMode,
                        new String[]{"hb1", "hb2", "9", "10", "11", "hy1", "hy2"},
                        new String[]{"blue", "blue", "white", "white", "white", "yellow", "yellow"}
                ),
                createPattern(gameMode,
                        new String[]{"hb3", "hb4", "8", "dy1", "12", "hy3", "hy4"},
                        new String[]{"blue", "blue", "white", "yellow", "white", "yellow", "yellow"}
                ),
                createPatternEmpty(gameMode,
                        new String[]{"7", "dy2", "13"},
                        new String[]{"white", "yellow", "white"}
                ),
                createPatternEmpty(gameMode,
                        new String[]{"6", "dy3", "14"},
                        new String[]{"white", "yellow", "white"}
                ),
                createLong(gameMode,
                        new String[]{"1", "2", "3", "4", "5", "dy4", "15", "16", "17", "18", "19"},
                        new String[]{"white", "white", "white", "white", "white", "yellow", "white", "white", "white", "white", "white"}
                ),
                createLongEmpty(gameMode,
                        new String[]{"40", "db1", "db2", "db3", "db4", "dr4", "dr3", "dr2", "dr1", "20"},
                        new String[]{"white", "blue", "blue", "blue", "blue", "red", "red", "red", "red", "white"}
                ),
                createLong(gameMode,
                        new String[]{"39", "38", "37", "36", "35", "dg4", "25", "24", "23", "22", "21"},
                        new String[]{"white", "white", "white", "white", "white", "green", "white", "white", "white", "white", "white"}
                ),
                createPatternEmpty(gameMode,
                        new String[]{"34", "dg3", "26"},
                        new String[]{"white", "green", "white"}
                ),
                createPatternEmpty(gameMode,
                        new String[]{"33", "dg2", "27"},
                        new String[]{"white", "green", "white"}
                ),
                createPattern(gameMode,
                        new String[]{"hg1", "hg2", "32", "dg1", "28", "hr1", "hr2"},
                        new String[]{"green", "green", "white", "green", "white", "red", "red"}
                ),
                createPattern(gameMode,
                        new String[]{"hg3", "hg4", "31", "30", "29", "hr3", "hr4"},
                        new String[]{"green", "green", "white", "white", "white", "red", "red"}
                )
        );
        return board;
    }

    public static HBox createPattern(final GameMode gameMode, final String[] fieldIDs, final String[] colors) {
        HBox pattern = new HBox();
        pattern.setPrefHeight(40);
        pattern.setStyle("-fx-background-color: white;");
        pattern.getChildren().addAll(createField(gameMode, fieldIDs[0], colors[0]), createField(gameMode, fieldIDs[1], colors[1]), emptyField(), emptyField(), createField(gameMode, fieldIDs[2], colors[2]), createField(gameMode, fieldIDs[3], colors[3]), createField(gameMode, fieldIDs[4], colors[4]), emptyField(), emptyField(), createField(gameMode, fieldIDs[5], colors[5]), createField(gameMode, fieldIDs[6], colors[6]));
        pattern.setAlignment(Pos.CENTER);
        pattern.setSpacing(5);
        return pattern;
    }

    public static HBox createPatternEmpty(final GameMode gameMode, final String[] fieldIDs, final String[] colors) {
        HBox pattern = new HBox();
        pattern.setPrefHeight(40);
        pattern.setStyle("-fx-background-color: white;");
        pattern.getChildren().addAll(emptyField(), emptyField(), emptyField(), emptyField(), createField(gameMode, fieldIDs[0], colors[0]), createField(gameMode, fieldIDs[1], colors[1]), createField(gameMode, fieldIDs[2], colors[2]), emptyField(), emptyField(), emptyField(), emptyField());
        pattern.setAlignment(Pos.CENTER);
        pattern.setSpacing(5);
        return pattern;
    }

    public static HBox createLong(final GameMode gameMode, final String[] fieldIDs, final String[] colors) {
        HBox pattern = new HBox();
        pattern.setPrefHeight(40);
        pattern.setPrefWidth(120);
        pattern.setStyle("-fx-background-color: white;");
        pattern.getChildren().addAll(createField(gameMode, fieldIDs[0], colors[0]), createField(gameMode, fieldIDs[1], colors[1]), createField(gameMode, fieldIDs[2], colors[2]), createField(gameMode, fieldIDs[3], colors[3]), createField(gameMode, fieldIDs[4], colors[4]), createField(gameMode, fieldIDs[5], colors[5]), createField(gameMode, fieldIDs[6], colors[6]), createField(gameMode, fieldIDs[7], colors[7]), createField(gameMode, fieldIDs[8], colors[8]), createField(gameMode, fieldIDs[9], colors[9]), createField(gameMode, fieldIDs[10], colors[10]));
        pattern.setAlignment(Pos.CENTER);
        pattern.setSpacing(5);

        return pattern;
    }

    public static HBox createLongEmpty(final GameMode gameMode, final String[] fieldIDs, final String[] colors) {
        HBox pattern = new HBox();
        pattern.setPrefHeight(40);
        pattern.setPrefWidth(120);
        pattern.setStyle("-fx-background-color: white;");
        pattern.getChildren().addAll(createField(gameMode, fieldIDs[0], colors[0]), createField(gameMode, fieldIDs[1], colors[1]), createField(gameMode, fieldIDs[2], colors[2]), createField(gameMode, fieldIDs[3], colors[3]), createField(gameMode, fieldIDs[4], colors[4]), emptyField(), createField(gameMode, fieldIDs[5], colors[5]), createField(gameMode, fieldIDs[6], colors[6]), createField(gameMode, fieldIDs[7], colors[7]), createField(gameMode, fieldIDs[8], colors[8]), createField(gameMode, fieldIDs[9], colors[9]));
        pattern.setAlignment(Pos.CENTER);
        pattern.setSpacing(5);
        return pattern;
    }

    public static Pane createField(final GameMode gameMode, final String id, final String color) {
        StackPane pattern = new StackPane();
//        Label label = new Label(id);
//        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: orange;");
//        pattern.getChildren().add(label);
        pattern.setMaxHeight(40);
        pattern.setPrefWidth(40);
        pattern.setStyle("-fx-background-color: " + color + ";-fx-border-style: solid solid solid solid; -fx-border-color: black black black black; ");
        gameMode.addField(id, null, pattern);
        pattern.setAlignment(Pos.CENTER);
        return pattern;
    }

    public static Pane emptyField() {
        Pane pattern = new Pane();
        pattern.setMaxHeight(40);
        pattern.setPrefWidth(40);
        pattern.setStyle("-fx-background-color: white;");
        return pattern;
    }
}

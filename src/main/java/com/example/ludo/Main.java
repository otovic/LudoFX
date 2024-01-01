package com.example.ludo;

import com.example.ludo.screens.JoinLobbyScreen;
import com.example.ludo.screens.LobbyScreen;
import com.example.ludo.screens.StartingScreen;
import com.example.ludo.session.Session;
import com.example.ludo.utility.ScreenController;
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

    public void initGame() {
        this.gameMode = new GameMode("test", "test", new LudoGameState());

        BorderPane v = new BorderPane();
        v.setPrefHeight(780);
        v.setPrefWidth(1300);
        v.setStyle("-fx-background-color: #ffffff;");

        HBox top = new HBox();
        top.setPrefHeight(120);
        top.setPrefWidth(1300);
        top.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        VBox player1 = new VBox();
        player1.setMaxHeight(100);
        player1.setPrefWidth(200);
        player1.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black red;");

        VBox middle = new VBox();
        middle.setMaxHeight(100);
        middle.setPrefWidth(850);
        middle.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        VBox player2 = new VBox();
        player2.setMaxHeight(100);
        player2.setPrefWidth(200);
        player2.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black red;");

        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        top.getChildren().addAll(player1, middle, player2);

        HBox boardContainer = new HBox();
        boardContainer.setStyle("-fx-background-color: red; -fx-border-style: solid solid solid solid; -fx-border-color: green black black red;");

        boardContainer.getChildren().addAll(createBoard(this.gameMode));

        VBox chatContainer = new VBox();
        chatContainer.setPrefHeight(540);
        chatContainer.setPrefWidth(220);
        chatContainer.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        VBox scoreBoards = new VBox();
        scoreBoards.setPrefHeight(540);
        scoreBoards.setPrefWidth(220);
        scoreBoards.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        HBox butum = new HBox();
        butum.setPrefHeight(120);
        butum.setPrefWidth(1300);
        butum.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        VBox player4 = new VBox();
        player4.setMaxHeight(100);
        player4.setPrefWidth(200);
        player4.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: red red black red;");

        VBox midBot = new VBox();
        midBot.setMaxHeight(100);
        midBot.setPrefWidth(850);
        midBot.setStyle("-fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");

        VBox player3 = new VBox();
        player3.setMaxHeight(100);
        player3.setPrefWidth(200);
        player3.setStyle("-fx-border-style: solid solid solid solid; -fx-border-radius: 10; -fx-border-color: black black black red;");

        butum.setAlignment(Pos.CENTER);
        butum.setSpacing(10);
        butum.getChildren().addAll(player4, midBot, player3);

        v.setTop(top);
        v.setLeft(chatContainer);
        v.setRight(scoreBoards);
        v.setCenter(boardContainer);
        v.setBottom(butum);

        Scene scene = new Scene(v, 1300, 780);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        gameMode.state.addPlayer("1", "petar", 1);
        gameMode.state.players.get("1").setPlayerBox(player1);
        gameMode.state.addPlayer("2", "svetozar", 2);
        gameMode.state.players.get("2").setPlayerBox(player2);
        gameMode.state.addPlayer("3", "tadzudin", 3);
        gameMode.state.players.get("3").setPlayerBox(player3);
        gameMode.state.addPlayer("4", "dimitar", 4);
        gameMode.state.players.get("4").setPlayerBox(player4);

        gameMode.start();
    }

    public void initGameMode() {

    }

    public static VBox createBoard(final GameMode gameMode) {
        VBox board = new VBox();
        board.setPrefWidth(900);
        board.setStyle("-fx-background-color: white; -fx-border-style: solid solid solid solid; -fx-border-color: black black black red;");
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


    public static void main(String[] args) {
        launch();
    }
}
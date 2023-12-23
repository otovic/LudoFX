package com.example.ludo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerState {
    public String username;
    public int color;
    public List<Figure> playerFigures;
    public VBox playerBox;

    private VBox diceRoller;

    private LudoGameState gameState;
    private int max = 0;

    public PlayerState(String username, List<Figure> playerFigures, int color, LudoGameState gameState) {
        this.username = username;
        this.playerFigures = playerFigures;
        this.color = color;
        this.gameState = gameState;
        this.max = this.setMax();
    }

    private int setMax() {
        if (this.color == 1) {
            return 40;
        } else if (this.color == 2) {
            return 10;
        } else if (this.color == 3) {
            return 20;
        } else if (this.color == 4) {
            return 30;
        }
        return 0;
    }

    public void setPlayerBox(VBox playerBox) {
        this.playerBox = playerBox;
        playerBox.setAlignment(Pos.CENTER);
        HBox data = new HBox();
        data.setPrefWidth(200);
        data.setPrefHeight(100);
        data.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(80);
        stackPane.setPrefWidth(80);
        Image image = new Image("musk.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(60);
        stackPane.getChildren().addAll(imageView);

        VBox playerInfo = new VBox();
        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.setPrefWidth(100);
        playerInfo.setMaxHeight(80);
//        playerInfo.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");

        StackPane pname = new StackPane();
        pname.setPrefHeight(10);
        pname.setPrefWidth(100);
        pname.setAlignment(Pos.CENTER);
        Text username = new Text(this.username);
        username.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #000000;");
        pname.getChildren().addAll(username);

        playerInfo.getChildren().addAll(pname);

        VBox inHouse = new VBox();
        inHouse.setAlignment(Pos.CENTER);
        inHouse.setPrefHeight(55);
        inHouse.setPrefWidth(100);
//        inHouse.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");

        this.diceRoller = inHouse;
        this.createHouse();
        playerInfo.getChildren().addAll(inHouse);

        data.getChildren().addAll(stackPane, playerInfo);
        playerBox.getChildren().addAll(data);
    }

    private StackPane createBoxy() {
        StackPane stackPane = new StackPane();
        stackPane.setMaxHeight(20);
        stackPane.setPrefWidth(20);
        stackPane.setStyle("-fx-background-color: white; -fx-background-radius: 50; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 50;");
        return stackPane;
    }

    public void createHouse() {
        this.diceRoller.getChildren().clear();
        HBox firstHouseRow = new HBox();
        firstHouseRow.setAlignment(Pos.CENTER);
        firstHouseRow.setPrefWidth(100);
        firstHouseRow.setPrefHeight(25);
//        firstHouseRow.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        firstHouseRow.setSpacing(20);
        firstHouseRow.getChildren().addAll(this.createBoxy(), this.createBoxy());

        HBox secondHouseRow = new HBox();
        secondHouseRow.setAlignment(Pos.CENTER);
        secondHouseRow.setPrefWidth(100);
        secondHouseRow.setPrefHeight(25);
//        secondHouseRow.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        secondHouseRow.setSpacing(20);
        secondHouseRow.getChildren().addAll(this.createBoxy(), this.createBoxy());
        this.diceRoller.getChildren().addAll(firstHouseRow, secondHouseRow);
    }

    private String genDice() {
        Random r = new Random();
        int dice = r.nextInt(6) + 1;
        switch (dice) {
            case 1 -> {
                return "one.png";
            }
            case 2 -> {
                return "two.png";
            }
            case 3 -> {
                return "three.png";
            }
            case 4 -> {
                return "four.png";
            }
            case 5 -> {
                return "five.png";
            }
            case 6 -> {
                return "six.png";
            }
        }
        return null;
    }

    public void createDiceRoller() {
        Image dice = new Image("one.png");
        ImageView diceView = new ImageView(dice);
        diceView.setOnMouseClicked(event -> {
            System.out.println("Clicked");
            Platform.runLater(() -> {
                Timeline timeline = new Timeline();
                Timeline timeline1 = new Timeline();
                for (int i = 1; i < 8; i++) {
                    Duration duration = Duration.millis(200 * i);
                    int finalI = i;
                    KeyFrame keyFrame = new KeyFrame(duration, event1 -> {
                        this.diceRoller.getChildren().remove(0);
                        String rolled = genDice();
                        Image dice1 = new Image(rolled);
                        ImageView diceView1 = new ImageView(dice1);
                        diceView1.setFitHeight(50);
                        diceView1.setFitWidth(50);
                        this.diceRoller.getChildren().addAll(diceView1);
                        if (finalI == 7) {
                            Duration duration1 = Duration.millis(1000);
                            KeyFrame key1 = new KeyFrame(duration1, event2 -> {
                                this.showPossibleMoves(rolled);
                            });
                            timeline1.getKeyFrames().add(key1);
                            timeline1.play();
                        }
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }
                timeline.play();
            });

        });
        diceView.setFitHeight(50);
        diceView.setFitWidth(50);
        this.diceRoller.getChildren().clear();
        this.diceRoller.getChildren().addAll(diceView);
    }

    private int getRolledInt(final String rolled) {
        switch (rolled) {
            case "one.png" -> {
                return 1;
            }
            case "two.png" -> {
                return 2;
            }
            case "three.png" -> {
                return 3;
            }
            case "four.png" -> {
                return 4;
            }
            case "five.png" -> {
                return 5;
            }
            case "six.png" -> {
                return 6;
            }
        }
        return 1;
    }

    private boolean canMoveToNewField(final String rolled, final String current) {
        final int roll = getRolledInt(rolled);
                int currentInt = Integer.parseInt(current);
                if (currentInt + roll > 40) {
                    return gameState.fields.get(String.valueOf(currentInt + roll - 40)).figure == null || gameState.fields.get(String.valueOf(currentInt + roll - 40)).figure.getIntColor() != this.color;
                } else {
                    return gameState.fields.get(String.valueOf(currentInt + roll)).figure == null || gameState.fields.get(String.valueOf(currentInt + roll)).figure.getIntColor() != this.color;
                }
    }

    public void showPossibleMoves(final String rlled) {
        final String rolled = "six.png";
        boolean canMove = false;
        for (Figure figure : this.playerFigures) {
            if (rolled.equals("six.png")) {
                if (figure.fieldID.startsWith("h")) {
                    String fieldNumber = this.getFirstFieldBasedOnColor(figure.figureID);
                    System.out.println(fieldNumber);
                    try {
                        System.out.println(gameState.fields.get(fieldNumber).figure.getIntColor());
                    }
                    catch (Exception e) {
                        System.out.println("No figure");
                    }
                    if (gameState.fields.get(fieldNumber).figure == null || gameState.fields.get(fieldNumber).figure.getIntColor() != this.color) {
                        canMove = true;
                        figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                        figure.figure.setOnMouseClicked(event -> {
                            this.playerFigures.stream()
                                    .forEach(f -> {
                                        f.figure.setOnMouseClicked(null);
                                        f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                    });
                            this.moveFigure(rolled, figure);
                        });
                    }
                } else if (!figure.fieldID.contains("d")) {
                    if (canMoveToNewField(rolled, figure.fieldID)) {
                        canMove = true;
                        figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                        figure.figure.setOnMouseClicked(event -> {
                            this.playerFigures.stream()
                                    .forEach(f -> {
                                        f.figure.setOnMouseClicked(null);
                                        f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                    });
                            this.moveFigure(rolled, figure);
                        });
                    }
                }
            }
            if (rolled.equals("one.png")) {
                if (!figure.fieldID.contains("d") && !figure.fieldID.contains("h")) {
                    canMove = true;
                    figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                    figure.figure.setOnMouseClicked(event -> {
                        this.playerFigures.stream()
                                .forEach(f -> {
                                    f.figure.setOnMouseClicked(null);
                                    f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                });
                        this.moveFigure(rolled, figure);
                    });
                }
            }
            if (rolled.equals("two.png")) {
                if (!figure.fieldID.contains("d") && !figure.fieldID.contains("h")) {
                    canMove = true;
                    figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                    figure.figure.setOnMouseClicked(event -> {
                        this.playerFigures.stream()
                                .forEach(f -> {
                                    f.figure.setOnMouseClicked(null);
                                    f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                });
                        this.moveFigure(rolled, figure);
                    });
                }
            }
            if (rolled.equals("three.png")) {
                if (!figure.fieldID.contains("d") && !figure.fieldID.contains("h")) {
                    canMove = true;
                    figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                    figure.figure.setOnMouseClicked(event -> {
                        this.playerFigures.stream()
                                .forEach(f -> {
                                    f.figure.setOnMouseClicked(null);
                                    f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                });
                        this.moveFigure(rolled, figure);
                    });
                }
            }
            if (rolled.equals("four.png")) {
                if (!figure.fieldID.contains("d") && !figure.fieldID.contains("h")) {
                    canMove = true;
                    figure.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 50;");
                    figure.figure.setOnMouseClicked(event -> {
                        this.playerFigures.stream()
                                .forEach(f -> {
                                    f.figure.setOnMouseClicked(null);
                                    f.figure.setStyle("-fx-background-color: " + figure.getHexColor() + "; -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 50;");
                                });
                        this.moveFigure(rolled, figure);
                    });
                }
            }
        }
        if (!canMove) {
            System.out.println(canMove);
            gameState.newPlayerTurn();
        }
    }

    public void moveFigure(final String rolled, final Figure figure) {
        if (rolled.equals("six.png")) {
            if (figure.fieldID.startsWith("h")) {
                gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                gameState.fields.get(figure.fieldID).figure = null;
                String firstField = getFirstFieldBasedOnColor(figure.figureID);
                this.removeChildrenFromField(firstField);
                gameState.fields.get(firstField).field.getChildren().add(figure.figure);
                gameState.fields.get(firstField).figure = figure;
                figure.fieldID = firstField;
                gameState.newPlayerTurn();
            } else if (!figure.fieldID.contains("d")) {
                gameState.fields.get(figure.fieldID).figure = null;
                int pos = Integer.parseInt(figure.fieldID);
                if (pos <= this.max && pos + 6 > this.max) {
                    gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                    gameState.fields.get("d" + String.valueOf(pos + 6 - this.max)).field.getChildren().add(figure.figure);
                    gameState.fields.get("d" + String.valueOf(pos + 6 - this.max)).color = this.color;
                } else {
                    Timeline timeline = new Timeline();
                    for (int i = 1; i < 7; i++) {
                        Duration duration = Duration.millis(500 * i);
                        int finalI = i;
                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
                            String currentPosition = "";
                            int nextPostiion = 0;
                            if (pos + finalI > 40) {
                                currentPosition = String.valueOf(pos + finalI - 40 - 1);
                                if (currentPosition.equals("0")) currentPosition = "40";
                                nextPostiion = pos + finalI - 40;
                            } else {
                                if (finalI == 1) {
                                    currentPosition = String.valueOf(pos);
                                    nextPostiion = pos + 1;
                                } else {
                                    currentPosition = String.valueOf(pos + finalI - 1);
                                    nextPostiion = pos + finalI;
                                }
                            }
                            if (gameState.fields.get(String.valueOf(currentPosition)).field.getChildren().size() > 1 && finalI != 6) {
                                gameState.fields.get(String.valueOf(currentPosition)).field.getChildren().remove(1,2);
                            } else {
                                gameState.fields.get(String.valueOf(currentPosition)).field.getChildren().clear();
                            }
                            if (finalI == 6) {
                                this.removeChildrenFromField(String.valueOf(nextPostiion));
                                gameState.fields.get(String.valueOf(nextPostiion)).field.getChildren().add(figure.figure);
                                gameState.fields.get(String.valueOf(nextPostiion)).figure = figure;
                                figure.fieldID = String.valueOf(nextPostiion);
                                gameState.newPlayerTurn();
                            } else {
                                gameState.fields.get(String.valueOf(nextPostiion)).field.getChildren().add(figure.figure);
                            }
                        });
                        timeline.getKeyFrames().add(keyFrame);
                    }
                    timeline.play();
                }
            }
        }
        if (rolled.equals("one.png")) {
            if (!figure.fieldID.contains("d")) {
                int pos = Integer.parseInt(figure.fieldID);
                if (pos <= this.max && pos + 1 > this.max) {
                    gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                    gameState.fields.get("d" + String.valueOf(pos + 1 - this.max)).field.getChildren().add(figure.figure);
                    gameState.fields.get("d" + String.valueOf(pos + 1 - this.max)).color = this.color;
                } else {
                    gameState.fields.get(figure.fieldID).figure = null;
                    Timeline timeline = new Timeline();
                    for (int i = 1; i < 2; i++) {
                        Duration duration = Duration.millis(500 * i);
                        int finalI = i;
                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
                            String position = "";
                            int positionInt = 0;
                            if (pos + finalI > 40) {
                                position = String.valueOf(pos + finalI - 40);
                                positionInt = pos + finalI - 40;
                            }   else {
                                position = String.valueOf(pos + finalI - 1);
                                positionInt = pos + finalI - 1;
                            }
                            if (finalI == 1) {
                                this.removeChildrenFromField(String.valueOf(positionInt + 1));
                                gameState.fields.get(String.valueOf(positionInt + 1)).field.getChildren().add(figure.figure);
                                gameState.fields.get(String.valueOf(positionInt + 1)).figure = figure;
                                figure.fieldID = String.valueOf(pos + finalI);
                                gameState.newPlayerTurn();
                            } else {
                                gameState.fields.get(String.valueOf(positionInt + 1)).field.getChildren().add(figure.figure);
                            }
                        });
                        timeline.getKeyFrames().add(keyFrame);
                    }
                    timeline.play();
                }
            }
        }
        if (rolled.equals("two.png")) {
            if (figure.fieldID.startsWith("h")) {
                gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                gameState.fields.get(getFirstFieldBasedOnColor(figure.figureID)).field.getChildren().add(figure.figure);
                gameState.fields.get(getFirstFieldBasedOnColor(figure.figureID)).color = this.color;
            } else if (!figure.fieldID.contains("d")) {
                gameState.fields.get(figure.fieldID).figure = null;
                gameState.fields.get(figure.fieldID).figure = null;
                int pos = Integer.parseInt(figure.fieldID);
                if (pos <= this.max && pos + 2 > this.max) {
                    gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                    gameState.fields.get("d" + String.valueOf(pos + 2 - this.max)).field.getChildren().add(figure.figure);
                    gameState.fields.get("d" + String.valueOf(pos + 2 - this.max)).color = this.color;
                } else {
                    Timeline timeline = new Timeline();
                    for (int i = 1; i < 3; i++) {
                        Duration duration = Duration.millis(500 * i);
                        int finalI = i;
                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
                            String position = "";
                            int positionInt = 0;
                            if (pos + finalI > 40) {
                                position = String.valueOf(pos + finalI - 40);
                                positionInt = pos + finalI - 40;
                            }   else {
                                position = String.valueOf(pos + finalI - 1);
                                positionInt = pos + finalI - 1;
                            }
                            if (gameState.fields.get(String.valueOf(position)).field.getChildren().size() > 1 && finalI == 2) {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().remove(1,2);
                            } else {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().clear();
                            }
                            if (finalI == 2) {
                                this.removeChildrenFromField(String.valueOf(pos + finalI));
                                gameState.fields.get(String.valueOf(pos + finalI)).field.getChildren().add(figure.figure);
                                gameState.fields.get(String.valueOf(pos + finalI)).figure = figure;
                                figure.fieldID = String.valueOf(pos + finalI);
                                gameState.newPlayerTurn();
                            } else {
                                gameState.fields.get(String.valueOf(positionInt + 1)).field.getChildren().add(figure.figure);
                            }
                        });
                        timeline.getKeyFrames().add(keyFrame);
                    }
                    timeline.play();
                }
            }
        }
        if (rolled.equals("three.png")) {
            if (figure.fieldID.startsWith("h")) {
                gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                gameState.fields.get(getFirstFieldBasedOnColor(figure.figureID)).field.getChildren().add(figure.figure);
                gameState.fields.get(getFirstFieldBasedOnColor(figure.figureID)).color = this.color;
            } else if (!figure.fieldID.contains("d")) {
                gameState.fields.get(figure.fieldID).figure = null;
                int pos = Integer.parseInt(figure.fieldID);
                if (pos <= this.max && pos + 3 > this.max) {
                    gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                    gameState.fields.get("d" + String.valueOf(pos + 3 - this.max)).field.getChildren().add(figure.figure);
                    gameState.fields.get("d" + String.valueOf(pos + 3 - this.max)).color = this.color;
                } else {
                    Timeline timeline = new Timeline();
                    for (int i = 1; i < 4; i++) {
                        Duration duration = Duration.millis(500 * i);
                        int finalI = i;
                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
                            String position = "";
                            int positionInt = 0;
                            if (pos + finalI > 40) {
                                position = String.valueOf(pos + finalI - 40);
                                positionInt = pos + finalI - 40;
                            }   else {
                                position = String.valueOf(pos + finalI - 1);
                                positionInt = pos + finalI - 1;
                            }
                            if (gameState.fields.get(String.valueOf(position)).field.getChildren().size() > 1 && finalI > 1) {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().remove(1,2);
                            } else {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().clear();
                            }
                            if (finalI == 3) {
                                this.removeChildrenFromField(String.valueOf(pos + finalI));
                                gameState.fields.get(String.valueOf(pos + finalI)).field.getChildren().add(figure.figure);
                                gameState.fields.get(String.valueOf(pos + finalI)).figure = figure;
                                figure.fieldID = String.valueOf(pos + finalI);
                                gameState.newPlayerTurn();
                            } else {
                                gameState.fields.get(String.valueOf(positionInt + 1)).field.getChildren().add(figure.figure);
                            }
                        });
                        timeline.getKeyFrames().add(keyFrame);
                    }
                    timeline.play();
                }
            }
        }
        if (rolled.equals("four.png")) {
            if (!figure.fieldID.contains("d")) {
                this.removeChildrenFromField(String.valueOf(Integer.parseInt(figure.fieldID)));
                int pos = Integer.parseInt(figure.fieldID);
                if (pos <= this.max && pos + 4 > this.max) {
                    gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
                    gameState.fields.get("d" + String.valueOf(pos + 4 - this.max)).field.getChildren().add(figure.figure);
                    gameState.fields.get("d" + String.valueOf(pos + 4 - this.max)).color = this.color;
                } else {
                    gameState.fields.get(figure.fieldID).figure = null;
                    Timeline timeline = new Timeline();
                    for (int i = 1; i < 5; i++) {
                        Duration duration = Duration.millis(500 * i);
                        int finalI = i;
                        KeyFrame keyFrame = new KeyFrame(duration, event -> {
                            String position = "";
                            int positionInt = 0;
                            if (pos + finalI > 40) {
                                position = String.valueOf(pos + finalI - 40);
                                positionInt = pos + finalI - 40;
                            }   else {
                                position = String.valueOf(pos + finalI - 1);
                                positionInt = pos + finalI - 1;
                            }
                            if (gameState.fields.get(String.valueOf(position)).field.getChildren().size() > 1 && finalI > 1) {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().remove(1,2);
                            } else {
                                gameState.fields.get(String.valueOf(position)).field.getChildren().clear();
                            }
                            if (finalI == 4) {
                                this.removeChildrenFromField(String.valueOf(pos + finalI));
                                gameState.fields.get(String.valueOf(pos + finalI)).field.getChildren().add(figure.figure);
                                gameState.fields.get(String.valueOf(pos + finalI)).figure = figure;
                                figure.fieldID = String.valueOf(pos + finalI);
                                gameState.newPlayerTurn();
                            } else {
                                gameState.fields.get(String.valueOf(positionInt + 1)).field.getChildren().add(figure.figure);
                            }
                        });
                        timeline.getKeyFrames().add(keyFrame);
                    }
                    timeline.play();
                }
            }
        }
    }

    private void removeChildrenFromField(final String fieldID) {
        Field field = gameState.fields.get(fieldID);
        if (field.figure != null) {
            Figure figure = field.figure;
            String presentFigureID = field.figure.getColor();
            for (int i = 1; i < 5; i++) {
                if (this.gameState.fields.get("h" + presentFigureID + i).figure == null) {
                    this.gameState.fields.get("h" + presentFigureID + i).figure = figure;
                    this.gameState.fields.get("h" + presentFigureID + i).field.getChildren().add(figure.figure);
                    figure.fieldID = "h" + presentFigureID + i;
                    break;
                }
            }
            gameState.fields.get(fieldID).field.getChildren().clear();
            gameState.fields.get(fieldID).color = null;
            gameState.fields.get(fieldID).figure = null;
        } else {
            gameState.fields.get(fieldID).field.getChildren().clear();
            gameState.fields.get(fieldID).color = null;
            gameState.fields.get(fieldID).figure = null;
        }
    }

    private void sendFigureHome(final Figure figure) {
        gameState.fields.get(figure.fieldID).field.getChildren().removeAll();
        gameState.fields.get(figure.fieldID).color = null;
        gameState.fields.get("h" + getFieldIDBasedOnColor(this.color) + figure.figureID.split("")[1]).field.getChildren().add(figure.figure);
        gameState.fields.get("h" + getFieldIDBasedOnColor(this.color) + figure.figureID.split("")[1]).color = this.color;
        figure.fieldID = "h" + getFieldIDBasedOnColor(this.color) + figure.figureID.split("")[1];
    }

    private String getFirstFieldBasedOnColor(final String color) {
        if (color.contains("b")) {
            return "1";
        } else if (color.contains("y")) {
            return "11";
        } else if (color.contains("r")) {
            return "21";
        } else if (color.contains("g")) {
            return "31";
        }
        return null;
    }

    private String getFieldIDBasedOnColor(final int color) {
        if (color == 1) {
            return "b";
        } else if (color == 2) {
            return "y";
        } else if (color == 3) {
            return "r";
        } else if (color == 4) {
            return "g";
        }
        return null;
    }

    public static void playSound(String soundFilePath) {

    }
}

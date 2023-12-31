package com.example.ludo;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LudoGameState {
    public HashMap<String, Field> fields = new HashMap<String, Field>();
    public HashMap<String, Player> players = new HashMap<String, Player>();
    public int turn = 1;

    public int blueDestinationReached = 0;
    public int yellowDestinationReached = 0;
    public int redDestinationReached = 0;
    public int greenDestinationReached = 0;

    public int playersAdded = 0;

    public void addField(final String id, final Player player, final Pane field) {
        this.fields.put(id, new Field(null, null, field));
    }

    public void addPlayer(final String id, final String username, final int color) {
        this.players.put(id, new Player(this.createFigures(), color, this));
        this.spawnPlayer(id, color);
        this.playersAdded++;
    }

    public Player getPlayer(final String id) {
        return this.players.get(id);
    }

    public void spawnPlayer(final String id, final int color) {
        String prefix = this.getPrefix();
        for (Figure figure : this.players.get(id).playerFigures) {
            Field f = this.fields.get(figure.fieldID);
            f.field.getChildren().add(figure.generateFigure());
            f.figure = figure;
            f.color = color;
        }
    }

    private String getPrefix() {
        if(playersAdded == 0) return "b";
        if(playersAdded == 1) return "y";
        if(playersAdded == 2) return "r";
        if(playersAdded == 3) return "g";
        return "";
    }

    public List<Figure> createFigures() {
        List<Figure> figures = new ArrayList<Figure>();
        String idPrefix = this.getPrefix();
        for (int i=0; i<4; i++) {
            figures.add(new Figure(idPrefix + String.valueOf(i + 1), "h" + idPrefix + String.valueOf(i + 1)));
        }
        return figures;
    }

    public int getGotHome(final String color) {
        if (color.equals("b")) {
            return this.blueDestinationReached;
        }
        if (color.equals("y")) {
            return this.yellowDestinationReached;
        }
        if (color.equals("r")) {
            return this.redDestinationReached;
        }
        if (color.equals("g")) {
            return this.greenDestinationReached;
        }
        return 0;
    }

    public void gotHome(final String color) {
        if (color.equals("b")) {
            this.blueDestinationReached++;
        }
        if (color.equals("y")) {
            this.yellowDestinationReached++;
        }
        if (color.equals("r")) {
            this.redDestinationReached++;
        }
        if (color.equals("g")) {
            this.greenDestinationReached++;
        }
    }

    public void newPlayerTurn() {
        if (this.turn + 1 > 4) {
            this.turn = 1;
        } else {
            this.turn++;
        }
        this.start();
    }

    public void samePlayerTurn() {
        this.start();
    }

    public void start() {
        if (this.turn == 1) {
            this.players.get("1").createDiceRoller();
            this.players.get("2").createHouse();
            this.players.get("3").createHouse();
            this.players.get("4").createHouse();
        }
        if (this.turn == 2) {
            this.players.get("2").createDiceRoller();
            this.players.get("1").createHouse();
            this.players.get("3").createHouse();
            this.players.get("4").createHouse();
        }
        if (this.turn == 3) {
            this.players.get("3").createDiceRoller();
            this.players.get("1").createHouse();
            this.players.get("2").createHouse();
            this.players.get("4").createHouse();
        }
        if (this.turn == 4) {
            this.players.get("4").createDiceRoller();
            this.players.get("1").createHouse();
            this.players.get("2").createHouse();
            this.players.get("3").createHouse();
        }
    }
}

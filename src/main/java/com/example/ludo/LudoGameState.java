package com.example.ludo;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LudoGameState {
    public HashMap<String, Field> fields = new HashMap<String, Field>();
    public HashMap<String, Player> players = new HashMap<String, Player>();

    public int blueDestinationReached = 0;
    public int yellowDestinationReached = 0;
    public int redDestinationReached = 0;
    public int greenDestinationReached = 0;

    public int playersAdded = 0;

    public void addField(final String id, final Player player, final Pane field) {
        this.fields.put(id, new Field(player, field));
    }

    public void addPlayer(final String id, final String username, final int color) {
        this.players.put(id, new Player(id, new PlayerState(username, this.createFigures(), color)));
        this.spawnPlayer(id, username);
        this.playersAdded++;
    }

    public Player getPlayer(final String id) {
        return this.players.get(id);
    }

    public void spawnPlayer(final String id, final String username) {
        String prefix = this.getPrefix();
        for (Figure figure : this.players.get(id).state.playerFigures) {
            this.fields.get(figure.fieldID).field.getChildren().add(figure.generateFigure());
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
}

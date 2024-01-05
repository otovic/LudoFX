package com.example.ludo.game;

import com.example.ludo.screens.GameOver;
import com.example.ludo.session.Session;
import com.example.ludo.utility.EventResponse;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMode {
    public String key;
    public String ownerID;

    public int pos = 1;
    public HashMap<String, Field> fields = new HashMap<String, Field>();
    public HashMap<String, Player> players = new HashMap<String, Player>();
    public int turn = 1;
    public int blueDestinationReached = 0;
    public int yellowDestinationReached = 0;
    public int redDestinationReached = 0;
    public int greenDestinationReached = 0;
    public int playersAdded = 0;
    public int playersReady = 0;

    public GameMode(String key, String ownerID) {
        this.key = key;
        this.ownerID = ownerID;
    }

    public GameMode(final String lobbyID, final String ownerID, final Player player) {
        this.key = lobbyID;
        this.ownerID = ownerID;
        this.players.put(player.key, player);
    }

    public void printState() {
        System.out.println(this.fields);
    }

    public void addField(final String id, final Player player, final Pane field) {
        this.fields.put(id, new Field(null, null, field));
    }

    public void playerJoined(final String key, final Player player) {
        this.players.put(key, player);
    }

    public void playerReady(final String key) {
        System.out.println("player ready");
        this.playersReady++;
        this.players.get(key).isReady = true;
    }

    public void playerUnready(final String key) {
        System.out.println("player unready");
        this.playersReady--;
        this.players.get(key).isReady = false;
    }

    public boolean arePlayersReady() {
        return this.playersReady == this.players.size();
    }

    public void playerLeft(final String key) {
        if (this.players.get(key).isReady) {
            this.playersReady--;
        }
        this.players.remove(key);
    }

    public void playerLeftGame(final String key, final Session session) {
        Player p = this.players.get(key);
        p.playerFigures.forEach(figure -> {
            p.removeChildrenFromField(figure.fieldID);
        });
        if (p.color == this.turn) {
            this.newPlayerTurn("one.png");
        }
        if (p.color == 1) {
            this.blueDestinationReached = 0;
        }
        if (p.color == 2) {
            this.yellowDestinationReached = 0;
        }
        if (p.color == 3) {
            this.redDestinationReached = 0;
        }
        if (p.color == 4) {
            this.greenDestinationReached = 0;
        }
        this.players.remove(key);

        if (session.player.key.equals(this.ownerID)) {
            if (this.players.size() == 0) {
                session.executeEvent(new EventResponse("gameOverDelete", new HashMap<>() {{
                    put("game", session.gameMode.key);
                }}, new HashMap<>()));
            }
        }

        if (this.players.size() == 1) {
            session.removeListener("game");
            session.executeEvent(new EventResponse("gameOver", new HashMap<>() {{
                put("game", session.gameMode.key);
            }}, new HashMap<>()));
            this.playersReady = 0;
            GameOver.init(session, this.players.get(this.players.keySet().toArray()[0]).username);
        }
    }

    public void initPlayer(final String id) {
        Player p = this.players.get(id);
        p.createFigures(this.createFigures(p.color));
        this.spawnPlayer(id, p.color);
        this.playersAdded++;
    }

    public Player getPlayer(final String id) {
        return this.players.get(id);
    }

    public void spawnPlayer(final String id, final int color) {
        String prefix = this.getPrefix(color);
        for (Figure figure : this.players.get(id).playerFigures) {
            Field f = this.fields.get(figure.fieldID);
            f.field.getChildren().add(figure.generateFigure());
            f.figure = figure;
            f.color = color;
        }
    }

    private String getPrefix(final int color) {
        if(color == 1) return "b";
        if(color == 2) return "y";
        if(color == 3) return "r";
        if(color == 4) return "g";
        return "";
    }

    public List<Figure> createFigures(final int color) {
        List<Figure> figures = new ArrayList<Figure>();
        String idPrefix = this.getPrefix(color);
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

    public void gotHome(final String color, final Session session, final String username) {
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
        if (this.blueDestinationReached == 4 || this.yellowDestinationReached == 4 || this.redDestinationReached == 4 || this.greenDestinationReached == 4) {
            session.executeEvent(new EventResponse("gameOver", new HashMap<>() {{
                put("game", session.gameMode.key);
            }}, new HashMap<>()));
            GameOver.init(session, username);
        }
    }

    public void newPlayerTurn(final String rolled) {
        if (!rolled.equals("six.png")) {
            if (this.turn + 1 > this.players.size()) {
                this.turn = 1;
            } else {
                this.turn++;
            }
            this.start();
        } else {
            this.samePlayerTurn();
        }
    }

    public void samePlayerTurn() {
        this.start();
    }

    public void start() {
        this.players.forEach((k, v) -> {
            if (v.color == this.turn) {
                v.createDiceRoller();
            } else {
                v.createHouse();
            }
        });
    }
}

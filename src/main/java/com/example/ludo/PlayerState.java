package com.example.ludo;

import java.util.List;

public class PlayerState {
    public String username;
    public List<Figure> playerFigures;

    public PlayerState(String username, List<Figure> playerFigures) {
        this.username = username;
        this.playerFigures = playerFigures;
    }
}

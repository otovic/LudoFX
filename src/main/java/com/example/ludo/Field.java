package com.example.ludo;

import javafx.scene.layout.Pane;

public class Field {
    public Player player;
    public Pane field;

    public Field(Player player, Pane field) {
        this.player = player;
        this.field = field;
    }
}

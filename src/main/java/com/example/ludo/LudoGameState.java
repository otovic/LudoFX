package com.example.ludo;

import javafx.scene.layout.Pane;

import java.util.HashMap;

public class LudoGameState {
    public HashMap<String, Field> fields = new HashMap<String, Field>();

    public void addField(final String id, final Player player, final Pane field) {
        this.fields.put(id, new Field(player, field));
    }
}

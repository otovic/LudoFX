package com.example.ludo;

import javafx.scene.layout.Pane;

public class Field {
    public Integer color;
    public Pane field;

    public Field(Integer color, Pane field) {
        this.color = color;
        this.field = field;
    }
}

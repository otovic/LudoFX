package com.example.ludo.game;

import javafx.scene.layout.Pane;

public class Field {
    public Integer color;
    public Figure figure;
    public Pane field;

    public Field(Integer color, Figure figure, Pane field) {
        this.color = color;
        this.figure = figure;
        this.field = field;
    }

    @Override
    public String toString() {
        return "Field{" +
                "color=" + color +
                ", figure=" + figure +
                ", field=" + field +
                '}';
    }
}

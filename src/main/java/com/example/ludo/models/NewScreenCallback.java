package com.example.ludo.models;

import javafx.scene.Scene;

@FunctionalInterface
public interface NewScreenCallback {
    void run(Scene screen, String title);
}

package com.example.ludo.utility;

import com.example.ludo.models.ParametrizedCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UtilityFX {
    public static VBox createInputSet(final String text, final ParametrizedCallback callback) {
        VBox inputSet = new VBox();
        inputSet.setMaxWidth(250);
        inputSet.setPrefHeight(50);
        inputSet.setAlignment(Pos.CENTER);

        Label lblText = new Label(text);
        lblText.setPrefWidth(250);

        TextField input = new TextField();
        input.setPrefWidth(250);
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            callback.run(newValue);
        });

        inputSet.getChildren().addAll(lblText, input);

        return inputSet;
    }
}

package com.example.ludo.utility;

import com.example.ludo.models.ParametrizedCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UtilityFX {
    public static VBox createInputSet(final String text, final ParametrizedCallback callback, final boolean isPassword, final String placeholder, final String fieldValue) {
        VBox inputSet = new VBox();
        inputSet.setMaxWidth(250);
        inputSet.setPrefHeight(50);
        inputSet.setAlignment(Pos.CENTER);

        Label lblText = new Label(text);
        lblText.setPrefWidth(250);

        if (!isPassword) {
            TextField input = new TextField();
            input.setPromptText(placeholder != null ? placeholder : "");
            input.setPrefWidth(250);
            input.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    callback.run(input.getText());
                }
            });

            if (fieldValue != null) {
                input.setText(fieldValue);
            }

            inputSet.getChildren().addAll(lblText, input);
            return inputSet;
        } else {
            PasswordField input = new PasswordField();
            input.setPrefWidth(250);
            input.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    callback.run(input.getText());
                }
            });

            inputSet.getChildren().addAll(lblText, input);
            return inputSet;
        }
    }
}

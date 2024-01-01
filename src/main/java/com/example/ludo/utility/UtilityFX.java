package com.example.ludo.utility;

import com.example.ludo.models.ParametrizedCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    public static StackPane createMessage(final String text, final String username, final boolean owner) {
        StackPane message = new StackPane();
        message.setPrefHeight(50);
        message.setPrefWidth(250);

        VBox msgContainer = new VBox();
        msgContainer.setMaxWidth(150);
        msgContainer.setPadding(new javafx.geometry.Insets(2, 2, 2, 2));

        Text lblText = new Text(text);
        lblText.setWrappingWidth(100);

        Text lblUsername = new Text(username + ":");
        lblUsername.setStyle("-fx-font-weight: bold;");

        if (owner) {
            msgContainer.setStyle("-fx-background-color: #4CAF50; -fx-border-radius: 5px; -fx-background-radius: 5px");
            message.setAlignment(Pos.CENTER_RIGHT);
        } else {
            msgContainer.setStyle("-fx-background-color: #f1f1f1; -fx-border-radius: 5px;");
            message.setAlignment(Pos.CENTER_LEFT);
        }

        msgContainer.getChildren().addAll(lblUsername, lblText);
        message.getChildren().addAll(msgContainer);
        return message;
    }
}

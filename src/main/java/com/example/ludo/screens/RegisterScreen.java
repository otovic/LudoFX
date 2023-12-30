package com.example.ludo.screens;

import com.example.ludo.models.NewScreenCallback;
import com.example.ludo.session.Session;
import com.example.ludo.utility.*;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class RegisterScreen {
    public Session session;

    public RegisterScreen(Session session) {
        this.session = session;
    }

    public void initRegisterScreen(final NewScreenCallback screen) {
        final String[] username = new String[1];
        final String[] email = new String[1];
        final String[] password = new String[1];
        final String[] confirmPassword = new String[1];
        final String[] response = new String[1];
        VBox container = new VBox();
        container.setPrefHeight(400);
        container.setPrefWidth(300);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        Button register = new Button("Register");
        register.setPrefHeight(30);
        register.setPrefWidth(250);

        Label error = new Label();
        error.setVisible(false);

        register.setOnMouseClicked(e -> {
            if (username[0] == null || email[0] == null || password[0] == null || confirmPassword[0] == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Please fill all the fields");
            }

            if (!password[0].equals(confirmPassword[0])) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Passwords do not match");
            }

            String url = Http.constructURL("http://localhost:8082/register", new Tuple[]{
                    new Tuple("username", username[0]),
                    new Tuple("email", email[0]),
                    new Tuple("password", password[0]),
                    new Tuple("confirmPassword", confirmPassword[0])
            });

            Thread t = new Thread(() -> {
                EventResponse data = new EventResponse("register", new HashMap<>() {{
                }}, new HashMap<>() {{
                    put("username", username[0]);
                    put("email", email[0]);
                    put("password", password[0]);
                    put("confirmPassword", confirmPassword[0]);
                }});
                this.session.establishConnection(data);
                Gson gson = new Gson();
//                if (res.eventData.get("result").equals("true")) {
//                    res = new EventResponse("connectPlayer", new HashMap<>() {{
//
//                    }}, new HashMap<>() {{
//                        put("username", username[0]);
//                        put("password", password[0]);
//                    }});
//
//                    this.session.addListener(new Listener((data) -> {
//                        System.out.println(data);
//                    }));
//
//                    this.session.establishConnection(res);
//                }
            });

            t.start();
        });

        container.getChildren().addAll(UtilityFX.createInputSet("Username", (value) -> {
            username[0] = value;
        }), UtilityFX.createInputSet("Email", (value) -> {
            email[0] = value;
        }), UtilityFX.createInputSet("Password", (value) -> {
            password[0] = value;
        }), UtilityFX.createInputSet("Confirm Password", (value) -> {
            confirmPassword[0] = value;
        }), register, error);

        screen.run(new Scene(container, 300, 400), "Register");
    }
}

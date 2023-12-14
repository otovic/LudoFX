package com.example.ludo;

public class Player {
    public String id;
    public PlayerState state;

    public Player(String id, PlayerState state) {
        this.id = id;
        this.state = state;
    }
}

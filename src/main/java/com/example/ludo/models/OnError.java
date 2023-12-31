package com.example.ludo.models;

@FunctionalInterface
public interface OnError {
    void call(String message);
}

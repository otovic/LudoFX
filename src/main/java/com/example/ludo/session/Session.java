package com.example.ludo.session;

import com.example.ludo.game.GameMode;
import com.example.ludo.game.Player;
import com.example.ludo.models.OnError;
import com.example.ludo.models.ParametrizedCallback;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.example.ludo.utility.ScreenController;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Session {
    public ScreenController screenController = new ScreenController();
    public PrintWriter outputStream;
    public List<Listener> listeners = new ArrayList<>();
    public Player player;
    private String serverAddress = "localhost";
    private int serverPort = 8082;
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    public void removeListener(final String id) {
        listeners.removeIf(listener -> listener.id.equals(id));
    }
    public GameMode gameMode;
    public Thread thread = new Thread();
    public HashMap<String, ParametrizedCallback> events = new HashMap<>();

    public Session() {
        System.out.println("Session created");
        this.thread.start();
    }

    public void setEvent(final String event, final ParametrizedCallback callback) {
        this.events.put(event, callback);
    }

    public boolean setServer(final String server) {
        try {
            final String[] serverData = server.split(":");
            this.serverAddress = serverData[0];
            this.serverPort = Integer.parseInt(serverData[1]);
            System.out.println(this.serverAddress);
            System.out.println(this.serverPort);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getServer() {
        if (this.serverAddress == null || this.serverPort == 0) {
            return null;
        }
        return this.serverAddress + ":" + this.serverPort;
    }

    public void setTask(final Runnable task) {
        this.thread = new Thread(task::run);
        this.thread.start();
    }

    public void initLobby(final String lobbyID, final Player player) {
        this.gameMode = new GameMode(lobbyID, player.key, player);
    }

    public void cancelLobby() {
        this.gameMode = null;
    }

    public void setControlledPlayer(final Player player) {
        this.player = player;
    }

    public void executeEvent(final EventResponse event) {
        this.outputStream.println(new Gson().toJson(event));
        this.outputStream.flush();
    }

    public void establishConnection(final EventResponse data, final String route, final OnError onError) {
        try {
            Socket socket = new Socket(this.serverAddress, this.serverPort);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter echo = new PrintWriter(socket.getOutputStream(), true);
            this.outputStream = echo;
            Scanner scanner = new Scanner(System.in);

            String body = new Gson().toJson(data);
            String request = "POST /" + route + " HTTP/1.1\r\n" +
                    "Host: " + this.serverAddress + " \r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + body.length() + "\r\n" +
                    "Connection: keep-alive\r\n\r\n" +
                    body;
            echo.println(request);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    break;
                }
                this.notifyListeners(line);
            }
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
            onError.call("Server not found!");
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
            onError.call("Server not found!");
        }
    }

    public void notifyListeners(final String data) {
        System.out.println(this.listeners);
        for (Listener listener : listeners) {
            listener.setData(data);
            listener.runNotify();
        }
    }

    public void logout() {
        this.executeEvent(new EventResponse("logout", new HashMap<>(), new HashMap<>()));
        this.player = null;
    }

    @Override
    public String toString() {
        return "Session{" +
                "screenController=" + screenController +
                ", outputStream=" + outputStream +
                ", listeners=" + listeners +
                ", player=" + player +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverPort=" + serverPort +
                ", gameMode=" + gameMode +
                ", thread=" + thread +
                ", events=" + events +
                '}';
    }
}

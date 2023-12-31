package com.example.ludo.session;

import com.example.ludo.Player;
import com.example.ludo.models.OnError;
import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
import com.example.ludo.utility.ScreenController;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Session {
    public ScreenController screenController = new ScreenController();
    public List<Listener> listeners = new ArrayList<>();
    public Player player;

    private String serverAddress;
    private int serverPort;

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(final String id) {
        listeners.removeIf(listener -> listener.id.equals(id));
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

    public void setControlledPlayer(final Player player) {
        this.player = player;
    }

    public void establishConnection(final EventResponse data, final String route, final OnError onError) {
        try (Socket socket = new Socket(this.serverAddress, this.serverPort)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter echo = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String body = new Gson().toJson(data);
            String request = "POST /" + route + " HTTP/1.1\r\n" +
                    "Host: localhost\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + body.length() + "\r\n" +
                    "Connection: keep-alive\r\n\r\n" +
                    body;
            echo.println(request);

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.isEmpty()) {
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
        for (Listener listener : listeners) {
            listener.setData(data);
            listener.runNotify();
        }
    }
}

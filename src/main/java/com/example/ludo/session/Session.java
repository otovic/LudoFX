package com.example.ludo.session;

import com.example.ludo.utility.EventResponse;
import com.example.ludo.utility.Listener;
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
    public List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    public void establishConnection(final EventResponse data) {
        try (Socket socket = new Socket("localhost", 8082)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter echo = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String body = new Gson().toJson(data);
            String request = "POST /connect HTTP/1.1\r\n" +
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyListeners(final String data) {
        for (Listener listener : listeners) {
            listener.setData(data);
            listener.runNotify();
        }
    }
}

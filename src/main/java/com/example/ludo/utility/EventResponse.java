package com.example.ludo.utility;

import java.util.HashMap;

public class EventResponse {
    public String eventName = "";
    public HashMap<String, String> eventData = new HashMap<>();
    public HashMap<String, String> eventParams = new HashMap<>();

    public EventResponse(String eventName, HashMap<String, String> eventData, HashMap<String, String> eventParams) {
        this.eventName = eventName;
        this.eventData = eventData;
        this.eventParams = eventParams;
    }
}

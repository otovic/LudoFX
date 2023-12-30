package com.example.ludo.utility;

import com.example.ludo.models.ParametrizedCallback;

public class Listener {
    public String data;
    public ParametrizedCallback callback;

    public Listener(ParametrizedCallback callback) {
        this.callback = callback;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void runNotify() {
        callback.run(data);
    }
}

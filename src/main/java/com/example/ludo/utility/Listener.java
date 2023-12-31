package com.example.ludo.utility;

import com.example.ludo.models.ParametrizedCallback;

public class Listener {
    public String id;
    public String data;
    public ParametrizedCallback callback;

    public Listener(final String id, final ParametrizedCallback callback) {
        this.id = id;
        this.callback = callback;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void runNotify() {
        callback.run(data);
    }
}

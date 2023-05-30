package com.example.springchat.model;

import com.example.springchat.enums.SocketType;

public class SocketModel<T> {
    private SocketType type;
    public T data;

    public SocketModel(SocketType type, T data) {
        this.type = type;
        this.data = data;
    }

    public SocketType getType() {
        return type;
    }

    public void setType(SocketType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

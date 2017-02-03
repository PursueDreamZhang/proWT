package com.weiteng.weitengapp.bean;

/**
 * Created by Admin on 2017/1/14.
 */

public class EventMessage<T> {
    private byte code;
    private T data;

    public EventMessage(byte code, T data) {
        this.code = code;
        this.data = data;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

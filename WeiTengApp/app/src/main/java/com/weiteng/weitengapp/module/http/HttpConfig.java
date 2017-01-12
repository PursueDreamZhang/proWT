package com.weiteng.weitengapp.module.http;

/**
 * Created by Admin on 2016/11/19.
 */

public class HttpConfig {
    public boolean debug;
    public long timeout;
    public long cacheTime;
    public String cacheDir;

    public HttpConfig() {
    }

    public HttpConfig(boolean debug, long timeout, long interval, long cacheTime, String cacheDir) {
        this.debug = debug;
        this.timeout = timeout;
        this.cacheTime = cacheTime;
        this.cacheDir = cacheDir;
    }
}

package com.weiteng.weitengapp.module.crash;

import java.io.Serializable;

/**
 * Created by Admin on 2016/11/8.
 */

public class CrashData implements Serializable {
    private String type = "unknow";
    private String clazz = "unknow";
    private String method = "unknow";
    private String line = "unknow";
    private String cause = "unknow";
    private String stackTrace = "unknow";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}

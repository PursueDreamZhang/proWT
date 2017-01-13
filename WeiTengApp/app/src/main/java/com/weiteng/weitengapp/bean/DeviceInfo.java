package com.weiteng.weitengapp.bean;

/**
 * Created by Admin on 2016/11/8.
 */

public class DeviceInfo {
    private String VER;
    private String SDK;

    private String BRAND;
    private String MODEL;

    private String CPU;
    private String MAC;
    private String IMEI;
    private String IMSI;

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getVER() {
        return VER;
    }

    public void setVER(String VER) {
        this.VER = VER;
    }

    public String getSDK() {
        return SDK;
    }

    public void setSDK(String SDK) {
        this.SDK = SDK;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("VER:\t" + VER + "\n");
        buffer.append("SDK:\t" + SDK + "\n");
        buffer.append("Brand:\t" + BRAND + "\n");
        buffer.append("Model:\t" + MODEL + "\n");
        buffer.append("CPU:\t" + CPU + "\n");
        buffer.append("MAC:\t" + MAC + "\n");
        buffer.append("IMEI:\t" + IMEI + "\n");
        buffer.append("IMSI:\t" + IMSI + "\n");

        return buffer.toString();
    }
}

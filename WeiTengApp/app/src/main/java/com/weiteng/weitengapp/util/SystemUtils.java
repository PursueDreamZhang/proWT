package com.weiteng.weitengapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;


import com.weiteng.weitengapp.module.crash.DeviceInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 2016/11/7.
 */

public class SystemUtils {
    public static String getTime(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
    }

    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isNetworkConnected(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isAvailable();
    }

    public static DeviceInfo dumpDeviceInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setVER(packageInfo.versionName + "_" + packageInfo.versionCode);
        deviceInfo.setSDK(Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        deviceInfo.setBRAND(Build.BRAND);
        deviceInfo.setMODEL(Build.MODEL);
        deviceInfo.setCPU(Build.CPU_ABI);
        deviceInfo.setMAC(wifiInfo.getMacAddress());
        deviceInfo.setIMEI(telephonyManager.getDeviceId());
        deviceInfo.setIMSI(telephonyManager.getSubscriberId());

        return deviceInfo;
    }

    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

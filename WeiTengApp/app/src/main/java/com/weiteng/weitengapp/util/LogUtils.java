package com.weiteng.weitengapp.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Admin on 2016/11/19.
 */

public class LogUtils {
    private static final byte LOG_LEVEL_NONE = 0x0;
    private static final byte LOG_LEVEL_DEBUG = 0x1;
    private static final byte LOG_LEVEL_INFO = 0x2;
    private static final byte LOG_LEVEL_WARN = 0x3;
    private static final byte LOG_LEVEL_ERROR = 0x4;
    private static final byte LOG_LEVEL_ALL = 0x5;

    public static boolean DEBUG = true;
    private static byte mLogLevel = LOG_LEVEL_ALL;

    private static String mLogDir;

    public static void init(String logDir) {
        mLogDir = logDir;
    }

    public static int getLogLevel() {
        return mLogLevel;
    }

    public static void setLogLevel(byte level) {
        mLogLevel = level;
    }

    public static void d(String tag, String msg) {
        if (DEBUG && mLogLevel >= LOG_LEVEL_DEBUG) {
            Log.d(tag, msg);
            saveToLog(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG && mLogLevel >= LOG_LEVEL_INFO) {
            Log.i(tag, msg);
            saveToLog(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG && mLogLevel >= LOG_LEVEL_WARN) {
            Log.w(tag, msg);
            saveToLog(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG && mLogLevel >= LOG_LEVEL_ERROR) {
            Log.e(tag, msg);
            saveToLog(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG && mLogLevel >= LOG_LEVEL_ALL) {
            Log.v(tag, msg);
            saveToLog(tag, msg);
        }
    }

    private static void saveToLog(String tag, String msg) {
        if (mLogDir == null) {
            return;
        }

        File file = FileUtils.getFile(mLogDir, "app.log");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.println("#" + SystemUtils.getTime("yyyy-MM-dd HH:mm:ss"));
        writer.println(tag + ":\n\t" + msg);
        writer.println();
        writer.close();
    }
}

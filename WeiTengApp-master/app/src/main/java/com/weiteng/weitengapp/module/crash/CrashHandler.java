package com.weiteng.weitengapp.module.crash;

/**
 * Created by Admin on 2016/11/7.
 */

import android.app.Application;
import android.content.Intent;


import com.weiteng.weitengapp.base.BaseApplication;
import com.weiteng.weitengapp.util.FileUtils;
import com.weiteng.weitengapp.util.SystemUtils;
import com.weiteng.weitengapp.util.ThreadUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    private BaseApplication mApp;
    private String mLogDir;
    private boolean mDebug;

    private CrashHandler() {
    }

    public static CrashHandler getCrashHandler() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }

        return mCrashHandler;
    }

    public static void init(Application app, String logDir, boolean debug, CrashSender.CrashSendInterface sendInterface) {
        CrashHandler handler = getCrashHandler();
        handler.mDebug = debug;
        handler.mLogDir = logDir;
        handler.mApp = (BaseApplication) app;
        Thread.setDefaultUncaughtExceptionHandler(handler);
        CrashSender.init(sendInterface);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Throwable thrb;
        if (throwable.getCause() == null) {
            thrb = throwable;
        } else {
            thrb = throwable.getCause();
        }
        final Throwable cause = thrb;
        if (SystemUtils.isSDCardMounted()) {
            ThreadUtils.runOnBackground(new Runnable() {
                @Override
                public void run() {
                    saveToLog(cause);
                }
            });
        }

        if (mDebug) {
            StackTraceElement stackTraceElement = cause.getStackTrace()[0];
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            cause.printStackTrace(printWriter);
            printWriter.flush();

            CrashData crashData = new CrashData();
            crashData.setType(cause.getClass().getName());
            crashData.setClazz(stackTraceElement.getClassName().replace(mApp.getPackageName(), ".."));
            crashData.setMethod(stackTraceElement.getMethodName());
            crashData.setLine(stackTraceElement.getLineNumber() + "");
            crashData.setCause(cause.getMessage());
            crashData.setStackTrace(stringWriter.toString());

            Intent intent = new Intent(mApp, CrashReportActivity.class);
            intent.putExtra("crash_data", crashData);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            mApp.startActivity(intent);
        }
        SystemUtils.exit();
    }

    private void saveToLog(Throwable throwable) {
        if (mLogDir == null) {
            return;
        }

        File file = FileUtils.getFile(mLogDir, "crash.log");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.length() == 0) {
            writer.println(SystemUtils.dumpDeviceInfo(mApp.getApplicationContext()).toString());
        }

        writer.println("#" + SystemUtils.getTime("yyyy-MM-dd HH:mm:ss"));
        throwable.printStackTrace(writer);
        writer.println();
        writer.close();
    }
}

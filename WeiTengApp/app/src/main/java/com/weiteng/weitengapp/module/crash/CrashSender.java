package com.weiteng.weitengapp.module.crash;

/**
 * Created by Admin on 2016/11/8.
 */

public class CrashSender {
    private static CrashSender mCrashSender;
    private CrashSendInterface mSendInterface;

    private CrashSender() {
    }

    public static CrashSender getCrashSender() {
        if (mCrashSender == null) {
            mCrashSender = new CrashSender();
        }
        return mCrashSender;
    }

    public static void init(CrashSendInterface sendInterface) {
        getCrashSender().mSendInterface = sendInterface;
    }

    public static boolean send(CrashData crashData) {
        if (mCrashSender.mSendInterface == null) {
            return false;
        }
        return mCrashSender.mSendInterface.send(crashData);
    }

    public interface CrashSendInterface {
        boolean send(CrashData crashData);
    }
}

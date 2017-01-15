package com.weiteng.weitengapp.util;

import android.app.Application;
import android.os.SystemClock;

import com.weiteng.weitengapp.base.BaseApplication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private static BaseApplication mApp;
    private static ThreadPoolExecutor mBackgroundExecutor;
    private static ThreadPoolExecutor mFileTransExecutor;

    public static void init(Application application) {
        mApp = (BaseApplication) application;
    }

    private static ThreadPoolExecutor getThreadPoolExecutor() {
        if (mBackgroundExecutor == null) {
            mBackgroundExecutor = new ThreadPoolExecutor(1, 3, 5, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());
        }

        return mBackgroundExecutor;
    }

    public static ThreadPoolExecutor getFileTransExecutor() {
        if (mFileTransExecutor == null) {
            mFileTransExecutor = new ThreadPoolExecutor(5, 20, 30, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());
        }

        return mFileTransExecutor;
    }

    public static void runOnForeground(Runnable task) {
        mApp.getHandler().post(task);
    }

    public static void runOnForeground(Runnable task, long timeout) {
        mApp.getHandler().postDelayed(task, timeout);
    }

    public static void runOnBackground(Runnable task) {
        getThreadPoolExecutor().execute(task);
    }

    public static void runOnBackground(final Runnable task, final long timeout) {
        getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(timeout);
                getThreadPoolExecutor().execute(task);
            }
        });
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}

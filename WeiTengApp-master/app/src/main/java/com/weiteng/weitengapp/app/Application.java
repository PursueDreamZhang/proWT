package com.weiteng.weitengapp.app;

import android.os.SystemClock;

import com.weiteng.weitengapp.base.BaseApplication;
import com.weiteng.weitengapp.module.crash.CrashData;
import com.weiteng.weitengapp.module.crash.CrashHandler;
import com.weiteng.weitengapp.module.crash.CrashSender;
import com.weiteng.weitengapp.util.LogUtils;
import com.weiteng.weitengapp.util.ResourceUtils;
import com.weiteng.weitengapp.util.SystemUtils;
import com.weiteng.weitengapp.util.ThreadUtils;


/**
 * Created by Admin on 2016/11/8.
 */

public class Application extends BaseApplication {
    private boolean isOPen = false;
    @Override
    public void onCreate() {
        super.onCreate();
        if (isOPen){
            CrashHandler.init(this, Configuration.DEFAULT_LOG_PATH, true, new CrashSender.CrashSendInterface() {
                @Override
                public boolean send(CrashData crashData) {
                    showToast("send");

                    ThreadUtils.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(3000);
                            SystemUtils.exit();
                        }
                    });

                    return true;
                }
            });
        }


        ThreadUtils.init(this);
        ResourceUtils.init(this);

        LogUtils.init(Configuration.DEFAULT_LOG_PATH);
    }
}

package com.weiteng.weitengapp.app;

import android.content.SharedPreferences;
import android.os.SystemClock;

import com.weiteng.weitengapp.base.BaseApplication;
import com.weiteng.weitengapp.module.crash.CrashData;
import com.weiteng.weitengapp.module.crash.CrashHandler;
import com.weiteng.weitengapp.module.crash.CrashSender;
import com.weiteng.weitengapp.module.http.HttpCookie;
import com.weiteng.weitengapp.util.LogUtils;
import com.weiteng.weitengapp.util.ResourceUtils;
import com.weiteng.weitengapp.util.SystemUtils;
import com.weiteng.weitengapp.util.ThreadUtils;

import java.util.Map;
import java.util.Set;


/**
 * Created by Admin on 2016/11/8.
 */

public class Application extends BaseApplication {
    private SharedPreferences cookiePrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        cookiePrefs = getSharedPreferences("cookie", MODE_PRIVATE);

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

        ThreadUtils.init(this);
        ResourceUtils.init(this);

        LogUtils.init(Configuration.DEFAULT_LOG_PATH);

        if (hasCookie()) {
            setApiCookie();
        }
    }

    public void setApiCookie() {
        HttpCookie cookie = new HttpCookie();
        Map<String, ?> map = cookiePrefs.getAll();
        for (String key : map.keySet()) {
            cookie.put(key, (String) map.get(key));
        }
        ApiManager.getApiManager().setCookie(cookie);
    }

    public void saveApiCookie() {
        HttpCookie cookie = ApiManager.getApiManager().getCookie();
        Set<String> keySet = cookie.keySet();
        for (String key : keySet) {
            cookiePrefs.edit().putString(key, cookie.get(key)).commit();
        }
    }

    public void clearCookie() {
        cookiePrefs.edit().clear().commit();
    }

    public boolean hasCookie() {
        return cookiePrefs.getAll().keySet().size() != 0;
    }

}

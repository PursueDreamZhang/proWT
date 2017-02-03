package com.weiteng.weitengapp.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

public class BaseApplication extends Application {
    private Toast mToast;
    private Handler mHandler;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public Handler getHandler() {
        return mHandler;
    }

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public SharedPreferences getPreference() {
        return mSharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mContext = getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
    }
}

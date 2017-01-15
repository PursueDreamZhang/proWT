package com.weiteng.weitengapp.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
    protected BaseApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (BaseApplication) getApplication();
    }

    protected void showToast(String msg) {
        mApp.showToast(msg);
    }

    protected abstract void initView();

    protected abstract void initData();
}

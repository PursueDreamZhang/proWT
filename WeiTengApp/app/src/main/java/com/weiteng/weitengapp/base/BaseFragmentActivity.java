package com.weiteng.weitengapp.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseFragmentActivity extends FragmentActivity {
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

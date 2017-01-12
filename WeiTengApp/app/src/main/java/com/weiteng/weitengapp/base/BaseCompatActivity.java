package com.weiteng.weitengapp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseCompatActivity extends AppCompatActivity {
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

package com.weiteng.weitengapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.util.ThreadUtils;

public class SplashActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        enterMainActivity(1000);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void enterMainActivity(long timeout){
        ThreadUtils.runOnForeground(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, timeout);
    }
}

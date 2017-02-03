package com.weiteng.weitengapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.util.ThreadUtils;

public class SplashActivity extends BaseCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        initView();
        initData();
        enterLoginActivity(3000);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    private void enterLoginActivity(long timeout) {
        ThreadUtils.runOnForeground(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, timeout);
    }
}

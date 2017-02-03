package com.weiteng.weitengapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.app.Configuration;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.EventMessage;
import com.weiteng.weitengapp.interf.IEventCode;
import com.weiteng.weitengapp.ui.view.SwitchButton;
import com.weiteng.weitengapp.util.FileUtils;
import com.weiteng.weitengapp.util.ThreadUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class SettingActivity extends BaseCompatActivity {
    private Toolbar tb_toolbar;
    private LinearLayout ll_accept_push;
    private LinearLayout ll_auto_update;
    private LinearLayout ll_clean_cache;
    private LinearLayout ll_update;
    private LinearLayout ll_about;
    private LinearLayout ll_logout;
    private SwitchButton sb_accept_push;
    private SwitchButton sb_auto_update;
    private TextView tv_cache_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.aty_setting_tb_toolbar);
        ll_accept_push = (LinearLayout) findViewById(R.id.aty_setting_ll_accept_push);
        ll_auto_update = (LinearLayout) findViewById(R.id.aty_setting_ll_auto_update);
        ll_clean_cache = (LinearLayout) findViewById(R.id.aty_setting_ll_clean_cache);
        ll_update = (LinearLayout) findViewById(R.id.aty_setting_ll_update);
        ll_about = (LinearLayout) findViewById(R.id.aty_setting_ll_about);
        ll_logout = (LinearLayout) findViewById(R.id.aty_setting_ll_logout);

        sb_accept_push = (SwitchButton) findViewById(R.id.aty_setting_sb_accept_push);
        sb_auto_update = (SwitchButton) findViewById(R.id.aty_setting_sb_auto_update);
        tv_cache_size = (TextView) findViewById(R.id.aty_setting_tv_cache_size);

        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ll_accept_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb_accept_push.toggle();
            }
        });

        ll_auto_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb_auto_update.toggle();
            }
        });

        ll_clean_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanCache();
            }
        });

        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUpdate();
            }
        });

        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about();
            }
        });

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        sb_accept_push.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                mApp.getPreference().edit().putBoolean("accecpt_push", isChecked).commit();
            }
        });

        sb_auto_update.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                mApp.getPreference().edit().putBoolean("auto_update", isChecked).commit();
            }
        });

    }

    @Override
    protected void initData() {
        boolean accecpt_push = mApp.getPreference().getBoolean("accecpt_push", true);
        boolean auto_update = mApp.getPreference().getBoolean("auto_update", true);

        sb_accept_push.setChecked(accecpt_push);
        sb_auto_update.setChecked(auto_update);

        long cacheSize = FileUtils.getFileSize(new File(Configuration.DEFAULT_PATH_PREFIX));
        tv_cache_size.setText(Formatter.formatFileSize(this, cacheSize));
    }

    private void cleanCache() {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                Glide.get(SettingActivity.this).clearDiskCache();
                File logDir = new File(Configuration.DEFAULT_LOG_PATH);
                File cacheDir = new File(Configuration.DEFAULT_CACHE_PATH);
                File downloadDir = new File(Configuration.DEFAULT_DOWNLOAD_PATH);

                if (logDir.exists()) {
                    File[] files = logDir.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                }

                if (cacheDir.exists()) {
                    File[] files = cacheDir.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                }

                if (downloadDir.exists()) {
                    File[] files = downloadDir.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                }

                ThreadUtils.runOnForeground(new Runnable() {
                    @Override
                    public void run() {
                        long cacheSize = FileUtils.getFileSize(new File(Configuration.DEFAULT_PATH_PREFIX));
                        tv_cache_size.setText(Formatter.formatFileSize(SettingActivity.this, cacheSize));
                    }
                });
            }
        });
    }

    private void checkUpdate() {
    }

    private void about() {
    }

    private void logout() {
        EventBus.getDefault().post(new EventMessage<Object>(IEventCode.EVENT_USER_LOGOUT, null));
        ApiManager.getApiManager().clearCookie();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

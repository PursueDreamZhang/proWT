package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;

public class PreorderActivity extends BaseCompatActivity {
    private Toolbar tb_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.aty_preorder_tb_toolbar);
        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}

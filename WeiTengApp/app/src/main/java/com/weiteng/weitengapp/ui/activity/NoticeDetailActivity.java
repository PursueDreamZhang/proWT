package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.NoticeDetailBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeDetailActivity extends BaseCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Toolbar tb_toolbar;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.aty_notice_detail_tb_toolbar);
        tv_title = (TextView) findViewById(R.id.aty_notice_detail_tv_title);
        tv_time = (TextView) findViewById(R.id.aty_notice_detail_tv_time);
        tv_content = (TextView) findViewById(R.id.aty_notice_detail_tv_content);

        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        NoticeDetailBean notice_detail = (NoticeDetailBean) getIntent().getSerializableExtra("notice_detail");
        tv_title.setText(notice_detail.NnoticeTitle);
        tv_time.setText("发布于" + sdf.format(new Date(Long.parseLong(notice_detail.NnoticeTime) * 1000)));
        tv_content.setText(Html.fromHtml(notice_detail.NnoticeContent));
    }
}

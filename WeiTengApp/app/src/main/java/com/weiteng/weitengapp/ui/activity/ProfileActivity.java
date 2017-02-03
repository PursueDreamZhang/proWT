package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.UserBean;
import com.weiteng.weitengapp.interf.IConstantCode;
import com.weiteng.weitengapp.util.ResourceUtils;

public class ProfileActivity extends BaseCompatActivity {
    private Toolbar tb_toolbar;
    private TextView tv_username;
    private TextView tv_state;
    private TextView tv_point;
    private LinearLayout ll_nickname;
    private TextView tv_nickname;
    private LinearLayout ll_moblie;
    private TextView tv_mobile;
    private LinearLayout ll_address;
    private TextView tv_address;
    private UserBean mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.aty_profile_tb_toolbar);
        tv_username = (TextView) findViewById(R.id.aty_profile_tv_username);
        tv_state = (TextView) findViewById(R.id.aty_profile_tv_state);
        tv_point = (TextView) findViewById(R.id.aty_profile_tv_point);
        ll_nickname = (LinearLayout) findViewById(R.id.aty_profile_ll_nickname);
        tv_nickname = (TextView) findViewById(R.id.aty_profile_tv_nickname);
        ll_moblie = (LinearLayout) findViewById(R.id.aty_profile_ll_mobile);
        tv_mobile = (TextView) findViewById(R.id.aty_profile_tv_mobile);
        ll_address = (LinearLayout) findViewById(R.id.aty_profile_ll_address);
        tv_address = (TextView) findViewById(R.id.aty_profile_tv_address);

        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mUserInfo.CuserProv + mUserInfo.CuserCity + mUserInfo.CuserAddress);
            }
        });
    }

    @Override
    protected void initData() {
        mUserInfo = (UserBean) getIntent().getSerializableExtra("user_info");
        tv_username.setText(mUserInfo.Cusername);
        tv_point.setText(mUserInfo.CuserPoint);
        tv_nickname.setText(mUserInfo.Cnickname);
        tv_mobile.setText(mUserInfo.CuserMobile);
        tv_address.setText(mUserInfo.CuserAddress);

        switch (Integer.parseInt(mUserInfo.CuserState)) {
            case IConstantCode.USER_STATE_NORMAL:
                tv_state.setTextColor(ResourceUtils.getColor(R.color.green));
                tv_state.setText("正常");
                break;
            case IConstantCode.USER_STATE_LOST:
                tv_state.setTextColor(ResourceUtils.getColor(R.color.orange));
                tv_state.setText("挂失");
                break;
            case IConstantCode.USER_STATE_LOCK:
                tv_state.setTextColor(ResourceUtils.getColor(R.color.red));
                tv_state.setText("锁定");
                break;
        }
    }
}
